package com.dsantos;

import java.util.HashMap;
import java.util.Map;

public class TaxRate {
    private Map<String, Map<Integer, Double>> stateYearTaxRates;

    public TaxRate(Map<String, Map<Integer, Double>> stateYearTaxRates) {
        this.stateYearTaxRates = new HashMap<>();
    }


}
