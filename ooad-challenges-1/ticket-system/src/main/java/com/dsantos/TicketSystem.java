package com.dsantos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketSystem {
    private final List<Show> shows;

    public TicketSystem() {
        this.shows = new ArrayList<>();
    }

    public void addShow(Show show) {
        shows.add(show);
    }

    public List<Show> getShows() {
        return List.copyOf(shows);
    }

    public Optional<Ticket> sellTicket(Show show, Zone zone, int seatNumber) {
        Optional<Seat> seatOpt = show.getVenue().getSeat(zone, seatNumber);
        if (seatOpt.isEmpty()) {
            return Optional.empty();
        }

        Seat seat = seatOpt.get();
        if (!show.isSeatAvailable(seat)) {
            return Optional.empty();
        }

        Ticket ticket = new Ticket(show, seat);
        show.addTicket(ticket);
        return Optional.of(ticket);
    }


    public List<Ticket> sellTickets(Show show, Zone zone, int quantity) {
        List<Seat> availableSeats = show.getAvailableSeats(zone);
        if (availableSeats.size() < quantity) {
            throw new IllegalArgumentException("Not enough available seats in the requested zone.");
        }

        List<Ticket> soldTickets = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            Seat seat = availableSeats.get(i);
            Ticket ticket = new Ticket(show, seat);
            show.addTicket(ticket);
            soldTickets.add(ticket);
        }

        return soldTickets;
    }

    public int getTotalTicketsSold(Show show) {
        return show.getTickets().size();
    }
}
