package com.dsantos.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Inventory {

    private final List<Guitar> guitars;

    public Inventory() {
        this.guitars = new ArrayList<>();
    }

    public void addGuitar(Guitar guitar) {
        guitars.add(guitar);
    }

    public Optional<Guitar> getGuitars(String serialNumber) {
        return guitars.stream()
                .filter(guitar -> guitar.serialNumber().equals(serialNumber))
                .findFirst();
    }
}
