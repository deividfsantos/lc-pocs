package com.dsantos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Ticket(UUID id, Show show, Seat seat, LocalDateTime purchaseTime, BigDecimal price) {

    public Ticket(Show show, Seat seat) {
        this(UUID.randomUUID(), show, seat, LocalDateTime.now(), seat.zone().getPrice());
    }
}
