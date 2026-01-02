package com.dsantos;

import java.util.*;

public class Venue {
    private final String name;
    private final Map<Zone, List<Seat>> zones;

    public Venue(String name, Map<Zone, Integer> zoneCapacities) {
        this.name = name;
        this.zones = new LinkedHashMap<>();

        for (var entry : zoneCapacities.entrySet()) {
            var zone = entry.getKey();
            int capacity = entry.getValue();
            var seats = new ArrayList<Seat>();
            for (int i = 1; i <= capacity; i++) {
                seats.add(new Seat(i, zone));
            }
            zones.put(zone, seats);
        }
    }

    public String getName() {
        return name;
    }

    public Integer getTotalCapacity() {
        return zones.values().stream().mapToInt(List::size).sum();
    }

    public Optional<Seat> getSeat(Zone zone, int seatNumber) {
        return zones.get(zone).stream()
                .filter(seat -> seat.number() == seatNumber)
                .findFirst();
    }

    public int getCapacity(Zone zone) {
        return zones.getOrDefault(zone, Collections.emptyList()).size();
    }

    public List<Seat> getSeatsInZone(Zone zone) {
        return List.copyOf(zones.getOrDefault(zone, Collections.emptyList()));
    }
}
