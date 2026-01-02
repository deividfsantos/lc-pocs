package com.dsantos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Show {
    private final String name;
    private final Venue venue;
    private final LocalDateTime dateTime;
    private final List<Ticket> tickets;

    public Show(String name, Venue venue, LocalDateTime dateTime) {
        this.name = name;
        this.venue = venue;
        this.dateTime = dateTime;
        this.tickets = new ArrayList<>();
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

    public boolean isSeatAvailable(Seat seat) {
        return tickets.stream().noneMatch(ticket -> ticket.seat().equals(seat));
    }

    public List<Seat> getAvailableSeats(Zone zone) {
        return venue.getSeatsInZone(zone).stream()
                .filter(this::isSeatAvailable)
                .toList();
    }

    void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public List<Ticket> getTickets() {
        return List.copyOf(tickets);
    }
}
