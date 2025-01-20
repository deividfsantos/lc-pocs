package com.dsantos;

import java.util.Map;

public record State(String name, Map<String, Double> productTaxRates) {

}
