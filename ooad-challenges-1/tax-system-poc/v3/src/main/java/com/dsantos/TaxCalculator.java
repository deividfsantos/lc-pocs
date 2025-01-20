package com.dsantos;

import java.math.BigDecimal;
import java.util.List;

public class TaxCalculator {
    private final List<Year> years;

    public TaxCalculator(List<Year> years) {
        this.years = years;
    }

    public BigDecimal calculateTax(Product product, String state, Integer year) {
        Double rate = getTaxes(product, new StateSpecification(state), new YearSpecification(year));
        return product.getPrice().multiply(BigDecimal.valueOf(rate));
    }

    private Double getTaxes(Product product, Specification<State> stateSpec, Specification<Year> yearSpec) {
        return years.stream()
                .filter(yearSpec::isSatisfiedBy)
                .flatMap(y -> y.states().stream())
                .filter(stateSpec::isSatisfiedBy)
                .map(s -> s.productTaxRates().get(product.getName()))
                .findFirst()
                .orElseThrow();
    }
}