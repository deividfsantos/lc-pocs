package com.dsantos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class LogisticSystemTest {

    @Test
    void testCalculateBestPriceWithValidShipment() {
        Shipment shipment = new Shipment(BigDecimal.valueOf(10), BigDecimal.valueOf(100), "New York");
        LogisticSystem system = new LogisticSystem();
        BigDecimal price = system.calculateBestPrice(shipment);
        Assertions.assertNotNull(price);
        Assertions.assertTrue(price.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testCalculateBestPriceWithZeroValues() {
        Shipment shipment = new Shipment(BigDecimal.ZERO, BigDecimal.ZERO, "Los Angeles");
        LogisticSystem system = new LogisticSystem();
        BigDecimal price = system.calculateBestPrice(shipment);
        Assertions.assertNotNull(price);
        // Depending on implementation, price may be zero or minimum allowed
        Assertions.assertTrue(price.compareTo(BigDecimal.ZERO) >= 0);
    }

    @Test
    void testCalculateBestPriceWithNullShipment() {
        LogisticSystem system = new LogisticSystem();
        Assertions.assertThrows(NullPointerException.class, () -> {
            system.calculateBestPrice(null);
        });
    }
}
