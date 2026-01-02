package com.dsantos;

public record Seat(int number, Zone zone) {
    public Seat {
        if (number <= 0) {
            throw new IllegalArgumentException("Seat number must be positive.");
        }
        if (zone == null) {
            throw new IllegalArgumentException("Zone cannot be null.");
        }
    }
}

