package com.dsantos;

import java.util.HashMap;
import java.util.Map;

public class TaxRate {
    private final Map<String, Map<Integer, Double>> stateYearTaxRates;

    public TaxRate() {
        this.stateYearTaxRates = new HashMap<>();
    }

    public void addTaxRate(String state, int year, double rate) {
        this.stateYearTaxRates.putIfAbsent(state, new HashMap<>());
        this.stateYearTaxRates.get(state).put(year, rate);
    }

    public double getTaxRate(String state, int year) {
        return this.stateYearTaxRates.getOrDefault(state, new HashMap<>()).getOrDefault(year, 0.0);
    }
}
