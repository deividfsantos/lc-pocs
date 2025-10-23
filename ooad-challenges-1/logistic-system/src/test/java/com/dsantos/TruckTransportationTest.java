package com.dsantos;

import com.dsantos.Shipment;
import com.dsantos.transportation.TruckTransportation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.math.BigDecimal;

class TruckTransportationTest {
    @Test
    void testCalculateFreightTypical() {
        Shipment shipment = new Shipment(BigDecimal.valueOf(5), BigDecimal.valueOf(30), "Road");
        TruckTransportation truck = new TruckTransportation();
        BigDecimal price = truck.calculateFreight(shipment);
        Assertions.assertNotNull(price);
        Assertions.assertTrue(price.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testCalculateFreightZeroValues() {
        Shipment shipment = new Shipment(BigDecimal.ZERO, BigDecimal.ZERO, "Road");
        TruckTransportation truck = new TruckTransportation();
        BigDecimal price = truck.calculateFreight(shipment);
        Assertions.assertNotNull(price);
        Assertions.assertEquals(0, price.compareTo(BigDecimal.ZERO));
    }

    @Test
    void testGetTransportationType() {
        TruckTransportation truck = new TruckTransportation();
        Assertions.assertEquals("Truck", truck.getTransportationType());
    }
}

