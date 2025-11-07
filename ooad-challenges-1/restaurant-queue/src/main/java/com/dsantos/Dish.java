package com.dsantos;

public enum Dish {
    SALAD("Salad", 5),
    SOUP("Soup", 10),
    STEAK("Steak", 20),
    FRIES("Fries", 8);

    private final String dish;
    private final int prepMinutes;

    Dish(String dish, int prepMinutes) {
        this.dish = dish;
        this.prepMinutes = prepMinutes;
    }

    public String dish() {
        return dish;
    }

    public int prepMinutes() {
        return prepMinutes;
    }
}
