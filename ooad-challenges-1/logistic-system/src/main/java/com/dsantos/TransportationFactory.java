package com.dsantos;

import com.dsantos.transportation.BoatTransportation;
import com.dsantos.transportation.RailTransportation;
import com.dsantos.transportation.TransportationStrategy;
import com.dsantos.transportation.TruckTransportation;

public class TransportationFactory {

    public static TransportationStrategy getTransportationStrategy(String type) {
        return switch (type.toLowerCase()) {
            case "truck" -> new TruckTransportation();
            case "rail" -> new RailTransportation();
            case "boat" -> new BoatTransportation();
            default -> throw new IllegalArgumentException("Unknown transportation type: " + type);
        };
    }
}
