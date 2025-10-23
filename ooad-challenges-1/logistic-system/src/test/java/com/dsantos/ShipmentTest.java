package com.dsantos;

import com.dsantos.Shipment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.math.BigDecimal;

class ShipmentTest {
    @Test
    void testRecordConstruction() {
        Shipment shipment = new Shipment(BigDecimal.valueOf(1), BigDecimal.valueOf(2), "Dest");
        Assertions.assertEquals(BigDecimal.valueOf(1), shipment.volume());
        Assertions.assertEquals(BigDecimal.valueOf(2), shipment.weight());
        Assertions.assertEquals("Dest", shipment.destination());
    }

    @Test
    void testRecordEquality() {
        Shipment s1 = new Shipment(BigDecimal.valueOf(1), BigDecimal.valueOf(2), "Dest");
        Shipment s2 = new Shipment(BigDecimal.valueOf(1), BigDecimal.valueOf(2), "Dest");
        Assertions.assertEquals(s1, s2);
    }

    @Test
    void testRecordHashCode() {
        Shipment s1 = new Shipment(BigDecimal.valueOf(1), BigDecimal.valueOf(2), "Dest");
        Shipment s2 = new Shipment(BigDecimal.valueOf(1), BigDecimal.valueOf(2), "Dest");
        Assertions.assertEquals(s1.hashCode(), s2.hashCode());
    }
}
