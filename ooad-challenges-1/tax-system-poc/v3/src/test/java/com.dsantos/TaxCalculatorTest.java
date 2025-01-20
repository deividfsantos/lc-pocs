//package com.dsantos;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class TaxCalculatorTest {
//
//    private TaxCalculator taxCalculator;
//
//    @BeforeEach
//    void setUp() {
//        List<Year> years = List.of(new Year(2024, List.of(
//                new State("California", Map.of("Laptop", 0.070, "Phone", 0.034)),
//                new State("New York", Map.of("Laptop", 0.085, "Phone", 0.024))
//        )), new Year(2023, List.of(
//                new State("California", Map.of("Laptop", 0.080, "Phone", 0.034)),
//                new State("New York", Map.of("Laptop", 0.090, "Phone", 0.024))
//        )));
//        this.taxCalculator = new TaxCalculator(years);
//    }
//
//    @Test
//    void calculateTax2023Laptop() {
//        Product product = new Product("Laptop", BigDecimal.valueOf(1000));
//        BigDecimal taxInNewYork = taxCalculator.calculateTax(product, "New York", 2023);
//        BigDecimal taxInCalifornia = taxCalculator.calculateTax(product, "California", 2023);
//        assertEquals(0, taxInNewYork.compareTo(BigDecimal.valueOf(90L)));
//        assertEquals(0, taxInCalifornia.compareTo(BigDecimal.valueOf(80L)));
//    }
//
//    @Test
//    void calculateTax2024Laptop() {
//        Product product = new Product("Laptop", BigDecimal.valueOf(1000));
//        BigDecimal taxInNewYork = taxCalculator.calculateTax(product, "New York", 2024);
//        BigDecimal taxInCalifornia = taxCalculator.calculateTax(product, "California", 2024);
//        assertEquals(0, taxInNewYork.compareTo(BigDecimal.valueOf(85L)));
//        assertEquals(0, taxInCalifornia.compareTo(BigDecimal.valueOf(70L)));
//    }
//
//
//    @Test
//    void calculateTax2023Phone() {
//        Product product = new Product("Phone", BigDecimal.valueOf(500));
//        BigDecimal taxInNewYork = taxCalculator.calculateTax(product, "New York", 2023);
//        BigDecimal taxInCalifornia = taxCalculator.calculateTax(product, "California", 2023);
//        assertEquals(0, taxInNewYork.compareTo(BigDecimal.valueOf(12)));
//        assertEquals(0, taxInCalifornia.compareTo(BigDecimal.valueOf(17)));
//    }
//
//    @Test
//    void calculateTax2024Phone() {
//        Product product = new Product("Phone", BigDecimal.valueOf(500));
//        BigDecimal taxInNewYork = taxCalculator.calculateTax(product, "New York", 2024);
//        BigDecimal taxInCalifornia = taxCalculator.calculateTax(product, "California", 2024);
//        assertEquals(0, taxInNewYork.compareTo(BigDecimal.valueOf(12)));
//        assertEquals(0, taxInCalifornia.compareTo(BigDecimal.valueOf(17)));
//    }
//}