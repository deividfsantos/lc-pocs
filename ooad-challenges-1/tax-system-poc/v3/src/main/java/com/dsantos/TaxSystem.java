package com.dsantos;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class TaxSystem {
    public static void main(String[] args) {
        Tax taxNy23 = new Tax("NY", 2023, "Laptop", BigDecimal.valueOf(0.085));
        Tax taxCa23 = new Tax("CA", 2023, "Laptop", BigDecimal.valueOf(0.095));
        Tax taxNy24 = new Tax("NY", 2024, "Laptop", BigDecimal.valueOf(0.070));
        Tax taxCa24 = new Tax("CA", 2024, "Laptop", BigDecimal.valueOf(0.095));
        
//        Product product = new Product("Laptop", BigDecimal.valueOf(1000L));

//        Year2024Tax year2024Tax = new Year2024Tax(List.of(
//                new State("California", Map.of("Laptop", 0.070, "Phone", 0.034)),
//                new State("New York", Map.of("Laptop", 0.085, "Phone", 0.024))
//        ));

//        Year2023Tax year2023Tax = new Year2023Tax(List.of(
//                new State("California", Map.of("Laptop", 0.080, "Phone", 0.034)),
//                new State("New York", Map.of("Laptop", 0.090, "Phone", 0.024))
//        ));


//        TaxCalculator calculator = new TaxCalculator(years);
//
//        BigDecimal taxInCalifornia = calculator.calculateTax(product, "California", 2023);
//        BigDecimal taxInNewYork = calculator.calculateTax(product, "New York", 2023);
//
//        System.out.println("Tax in California: $" + taxInCalifornia + " for product " + product.getName());
//        System.out.println("Tax in New York: $" + taxInNewYork + " for product " + product.getName());
    }
}