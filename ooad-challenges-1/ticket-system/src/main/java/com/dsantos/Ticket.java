package com.dsantos;

import java.time.LocalDateTime;
import java.util.UUID;

public record Ticket(UUID id, UUID showId, String zone, int seatNumber, double price, String buyer, LocalDateTime soldAt) {
    public Ticket(UUID showId, String zone, int seatNumber, double price, String buyer) {
        this(UUID.randomUUID(), showId, zone, seatNumber, price, buyer, LocalDateTime.now());
    }
}
