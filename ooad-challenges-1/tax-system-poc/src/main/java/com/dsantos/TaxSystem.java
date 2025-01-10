package com.dsantos;

import java.math.BigDecimal;

public class TaxSystem {
    public static void main(String[] args) {
        Product product = new Product("Laptop", BigDecimal.valueOf(1000L));
        TaxRate taxRate = new TaxRate();
        taxRate.addTaxRate("California", 2024, 0.075);
        taxRate.addTaxRate("New York", 2024, 0.085);

        TaxCalculator calculator = new TaxCalculator(taxRate);

        BigDecimal taxInCalifornia = calculator.calculateTax(product, "California", 2024);
        BigDecimal taxInNewYork = calculator.calculateTax(product, "New York", 2024);

        System.out.println("Tax in California: $" + taxInCalifornia + " for product " + product.getName());
        System.out.println("Tax in New York: $" + taxInNewYork + " for product " + product.getName());
    }
}