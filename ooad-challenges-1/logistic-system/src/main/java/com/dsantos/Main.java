package com.dsantos;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Freight Calculation System!");
        Shipment shipment1 = new Shipment(BigDecimal.valueOf(100), BigDecimal.valueOf(200), "New York");
        Shipment shipment2 = new Shipment(BigDecimal.valueOf(50), BigDecimal.valueOf(80), "Chicago");
        Shipment shipment3 = new Shipment(BigDecimal.valueOf(200), BigDecimal.valueOf(300), "Miami");

        LogisticSystem logisticSystem = new LogisticSystem();
        BigDecimal bestPrice1 = logisticSystem.calculateBestPrice(shipment1);
        BigDecimal bestPrice2 = logisticSystem.calculateBestPrice(shipment2);
        BigDecimal bestPrice3 = logisticSystem.calculateBestPrice(shipment3);
        System.out.println("Best price for shipment 1: " + bestPrice1);
        System.out.println("Best price for shipment 2: " + bestPrice2);
        System.out.println("Best price for shipment 3: " + bestPrice3);
    }
}