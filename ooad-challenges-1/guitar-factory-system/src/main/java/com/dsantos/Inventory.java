package com.dsantos;

import com.dsantos.model.Guitar;
import com.dsantos.model.GuitarSpec;

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

    public List<Guitar> search(GuitarSpec searchSpec) {
        return guitars.stream()
                .filter(guitar -> guitar.spec().equals(searchSpec))
                .toList();
    }

    public List<Guitar> getAllGuitars() {
        return new ArrayList<>(guitars);
    }
}
