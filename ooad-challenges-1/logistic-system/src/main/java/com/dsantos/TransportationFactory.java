package com.dsantos;

import com.dsantos.transportation.*;

public class TransportationFactory {

    public static TransportationStrategy getTransportationStrategy(TransportationType type) {
        return switch (type) {
            case TransportationType.TRUCK -> new TruckTransportation();
            case TransportationType.RAIL -> new RailTransportation();
            case TransportationType.BOAT -> new BoatTransportation();
        };
    }
}
