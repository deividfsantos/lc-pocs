package com.dsantos;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Show {
    private final String name;
    private final Venue venue;
    private final LocalDateTime dateTime;
    private final Set<Seat> soldSeats;

    public Show(String name, Venue venue, LocalDateTime dateTime) {
        this.name = name;
        this.venue = venue;
        this.dateTime = dateTime;
        this.soldSeats = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Venue getVenue() {
        return venue;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Set<Seat> getSoldSeats() {
        return soldSeats;
    }

    public boolean reserveSeat(Seat seat) {
        if (soldSeats.contains(seat)) {
            return false;
        }
        return soldSeats.add(seat);
    }

    public int getAvailableSeatsCount(Zone zone) {
        long soldInZone = soldSeats.stream()
                .filter(seat -> seat.zone() == zone)
                .count();
        return venue.getCapacity(zone) - (int) soldInZone;
    }
}
