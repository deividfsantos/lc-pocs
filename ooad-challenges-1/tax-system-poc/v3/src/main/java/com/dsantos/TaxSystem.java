package com.dsantos;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class TaxSystem {
    public static void main(String[] args) {
        Product product = new Product("Laptop", BigDecimal.valueOf(1000L));

//        Year2024Tax year2024Tax = new Year2024Tax(List.of(
//                new State("California", Map.of("Laptop", 0.070, "Phone", 0.034)),
//                new State("New York", Map.of("Laptop", 0.085, "Phone", 0.024))
//        ));

        Year2023Tax year2023Tax = new Year2023Tax(List.of(
                new State("California", Map.of("Laptop", 0.080, "Phone", 0.034)),
                new State("New York", Map.of("Laptop", 0.090, "Phone", 0.024))
        ));


//        TaxCalculator calculator = new TaxCalculator(years);
//
//        BigDecimal taxInCalifornia = calculator.calculateTax(product, "California", 2023);
//        BigDecimal taxInNewYork = calculator.calculateTax(product, "New York", 2023);
//
//        System.out.println("Tax in California: $" + taxInCalifornia + " for product " + product.getName());
//        System.out.println("Tax in New York: $" + taxInNewYork + " for product " + product.getName());
    }
}