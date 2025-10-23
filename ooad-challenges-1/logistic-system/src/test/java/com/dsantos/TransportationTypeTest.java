package com.dsantos;

import com.dsantos.transportation.TransportationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class TransportationTypeTest {
    @Test
    void testEnumValues() {
        Assertions.assertEquals(3, TransportationType.values().length);
        Assertions.assertTrue(java.util.Arrays.asList(TransportationType.values()).contains(TransportationType.TRUCK));
        Assertions.assertTrue(java.util.Arrays.asList(TransportationType.values()).contains(TransportationType.RAIL));
        Assertions.assertTrue(java.util.Arrays.asList(TransportationType.values()).contains(TransportationType.BOAT));
    }

    @Test
    void testEnumName() {
        Assertions.assertEquals("TRUCK", TransportationType.TRUCK.name());
        Assertions.assertEquals("RAIL", TransportationType.RAIL.name());
        Assertions.assertEquals("BOAT", TransportationType.BOAT.name());
    }
}

