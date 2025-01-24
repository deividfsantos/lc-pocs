package com.dsantos;

import java.math.BigDecimal;

public class TaxCalculator {

    public BigDecimal calculateTax(Product product, String state, Year year) {
        Double rate = getTaxes(product, new StateSpecification(state), new YearSpecification(year), year);
        return product.getPrice().multiply(BigDecimal.valueOf(rate));
    }

    private Double getTaxes(Product product, Specification<State> stateSpec, Specification<Year> yearSpec, Year year) {
        if (yearSpec.isSatisfiedBy(year))
            year.states().stream()
                    .filter(stateSpec::isSatisfiedBy)
                    .map(s -> s.productTaxRates().get(product.getName()))
                    .findFirst()
                    .orElseThrow();
        throw new RuntimeException();
    }
}