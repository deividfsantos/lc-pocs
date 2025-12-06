package com.dsantos;

import com.dsantos.model.*;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        Guitar guitar1 = GuitarFactory.createGuitar(Builder.FENDER, Model.SG, Wood.ROSEWOOD, Wood.MAHOGANY, 6, BigDecimal.valueOf(16020.23));
        Guitar guitar2 = GuitarFactory.createGuitar(Builder.PRS, Model.LES_PAUL, Wood.ROSEWOOD, Wood.ROSEWOOD, 6, BigDecimal.valueOf(16020.23));
        Guitar guitar3 = GuitarFactory.createGuitar(Builder.GIBSON, Model.SG, Wood.SITKA, Wood.MAHOGANY, 6, BigDecimal.valueOf(16020.23));
        Guitar guitar4 = GuitarFactory.createGuitar(Builder.MARTIN, Model.STRATOCASTER, Wood.ROSEWOOD, Wood.MAHOGANY, 6, BigDecimal.valueOf(16020.23));
        inventory.addGuitar(guitar1);
        inventory.addGuitar(guitar2);
        inventory.addGuitar(guitar3);
        inventory.addGuitar(guitar4);

        inventory.getAllGuitars().forEach(guitar -> {
            System.out.println("Guitar: " + guitar);
        });

        inventory.search(new GuitarSpec(null, Model.SG, null, null, 6)).forEach(guitar -> {
            System.out.println("Found Guitar: " + guitar);
        });
    }
}