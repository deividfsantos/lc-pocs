package com.dsantos;

import java.util.Objects;

public record DishEstimate(Dish dish, int startsInMinutes, int readyInMinutes) {
    public DishEstimate(Dish dish, int startsInMinutes, int readyInMinutes) {
        this.dish = Objects.requireNonNull(dish);
        if (startsInMinutes < 0 || readyInMinutes < 0) throw new IllegalArgumentException("times must be >= 0");
        if (readyInMinutes < startsInMinutes) throw new IllegalArgumentException("readyIn must be >= startsIn");
        this.startsInMinutes = startsInMinutes;
        this.readyInMinutes = readyInMinutes;
    }
}