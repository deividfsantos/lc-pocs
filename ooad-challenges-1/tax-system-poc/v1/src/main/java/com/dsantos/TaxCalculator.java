package com.dsantos;

import java.math.BigDecimal;
import java.util.List;

public class TaxCalculator {
    private final List<Year> years;

    public TaxCalculator(List<Year> years) {
        this.years = years;
    }

    public BigDecimal calculateTax(Product product, String state, Integer year) {
        Double rate = getTaxes(product, state, year);
        return product.getPrice().multiply(BigDecimal.valueOf(rate));
    }

    private Double getTaxes(Product product, String state, Integer year) {
        return years.stream()
                .filter(y -> y.year().equals(year))
                .flatMap(y -> y.states().stream())
                .filter(s -> s.name().equals(state))
                .map(s -> s.productTaxRates().get(product.getName()))
                .findFirst()
                .orElseThrow();
    }
}