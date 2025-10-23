package com.dsantos;

import com.dsantos.Shipment;
import com.dsantos.transportation.RailTransportation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.math.BigDecimal;

class RailTransportationTest {
    @Test
    void testCalculateFreightTypical() {
        Shipment shipment = new Shipment(BigDecimal.valueOf(20), BigDecimal.valueOf(50), "Station");
        RailTransportation rail = new RailTransportation();
        BigDecimal price = rail.calculateFreight(shipment);
        Assertions.assertNotNull(price);
        Assertions.assertTrue(price.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testCalculateFreightZeroValues() {
        Shipment shipment = new Shipment(BigDecimal.ZERO, BigDecimal.ZERO, "Station");
        RailTransportation rail = new RailTransportation();
        BigDecimal price = rail.calculateFreight(shipment);
        Assertions.assertNotNull(price);
        Assertions.assertEquals(0, price.compareTo(BigDecimal.ZERO));
    }

    @Test
    void testGetTransportationType() {
        RailTransportation rail = new RailTransportation();
        Assertions.assertEquals("Rail", rail.getTransportationType());
    }
}

