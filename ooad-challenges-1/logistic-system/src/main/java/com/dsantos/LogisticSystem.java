package com.dsantos;

import com.dsantos.transportation.TransportationStrategy;
import com.dsantos.transportation.TransportationType;

import java.math.BigDecimal;

public class LogisticSystem {

    public BigDecimal calculateBestPrice(Shipment shipment) {
        BigDecimal bestPrice = BigDecimal.valueOf(Double.MAX_VALUE);
        String bestTransport = "";

        for (var transport : TransportationType.values()) {
            TransportationStrategy transportationStrategy = TransportationFactory.getTransportationStrategy(transport);
            BigDecimal price = transportationStrategy.calculateFreight(shipment);
            if (price.compareTo(bestPrice) < 0) {
                bestPrice = price;
                bestTransport = transport.name();
            }
        }
        System.out.println("Best transport method: " + bestTransport + " with price: " + bestPrice);
        return bestPrice;
    }
}
