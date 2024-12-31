package com.dsantos;

import com.dsantos.TaxRate;

import java.math.BigDecimal;

public class TaxCalculator {
    private TaxRate taxRate;

    public TaxCalculator(TaxRate taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal calculateTax(Product product, String state, int year) {
        double rate = taxRate.getTaxRate(state, year);
        return product.getPrice().multiply(BigDecimal.valueOf(rate));
    }
}