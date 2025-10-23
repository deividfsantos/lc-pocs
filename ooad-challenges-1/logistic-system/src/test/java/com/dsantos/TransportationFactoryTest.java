package com.dsantos;

import com.dsantos.transportation.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class TransportationFactoryTest {
    @Test
    void testGetTransportationStrategyTruck() {
        TransportationStrategy strategy = TransportationFactory.getTransportationStrategy(TransportationType.TRUCK);
        Assertions.assertInstanceOf(TruckTransportation.class, strategy);
        Assertions.assertEquals("Truck", strategy.getTransportationType());
    }

    @Test
    void testGetTransportationStrategyRail() {
        TransportationStrategy strategy = TransportationFactory.getTransportationStrategy(TransportationType.RAIL);
        Assertions.assertInstanceOf(RailTransportation.class, strategy);
        Assertions.assertEquals("Rail", strategy.getTransportationType());
    }

    @Test
    void testGetTransportationStrategyBoat() {
        TransportationStrategy strategy = TransportationFactory.getTransportationStrategy(TransportationType.BOAT);
        Assertions.assertInstanceOf(BoatTransportation.class, strategy);
        Assertions.assertEquals("Boat", strategy.getTransportationType());
    }
}

