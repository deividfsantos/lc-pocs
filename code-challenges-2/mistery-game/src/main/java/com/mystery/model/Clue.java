package com.mystery.model;

public record Clue(String id, String description, String location, boolean found) {
    public Clue withFound() {
        return new Clue(id, description, location, true);
    }
}
