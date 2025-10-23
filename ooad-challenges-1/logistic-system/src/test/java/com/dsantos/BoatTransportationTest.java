package com.dsantos;

import com.dsantos.Shipment;
import com.dsantos.transportation.BoatTransportation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.math.BigDecimal;

class BoatTransportationTest {
    @Test
    void testCalculateFreightTypical() {
        Shipment shipment = new Shipment(BigDecimal.valueOf(10), BigDecimal.valueOf(100), "Port");
        BoatTransportation boat = new BoatTransportation();
        BigDecimal price = boat.calculateFreight(shipment);
        Assertions.assertNotNull(price);
        Assertions.assertTrue(price.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testCalculateFreightZeroValues() {
        Shipment shipment = new Shipment(BigDecimal.ZERO, BigDecimal.ZERO, "Port");
        BoatTransportation boat = new BoatTransportation();
        BigDecimal price = boat.calculateFreight(shipment);
        Assertions.assertNotNull(price);
        Assertions.assertEquals(0, price.compareTo(BigDecimal.ZERO));
    }

    @Test
    void testGetTransportationType() {
        BoatTransportation boat = new BoatTransportation();
        Assertions.assertEquals("Boat", boat.getTransportationType());
    }
}
