package com.dsantos;

import com.dsantos.model.*;

import java.math.BigDecimal;

public class GuitarFactory {
    public static Guitar createGuitar(Builder builder, Model model, Wood backWood, Wood topWood, int numStrings, BigDecimal price) {
        GuitarSpec guitarSpec = new GuitarSpec(builder, model, backWood, topWood, numStrings);
        return new Guitar(generateSerialNumber(), price, guitarSpec);
    }

    private static String generateSerialNumber() {
        return "SN" + System.currentTimeMillis();
    }
}
