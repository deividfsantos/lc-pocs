package com.dsantos.transportation;

import com.dsantos.Shipment;

import java.math.BigDecimal;

public class BoatTransportation implements TransportationStrategy {

    private static final BigDecimal BASE_RATE = new BigDecimal("1.2");
    private static final BigDecimal VOLUME_RATE = new BigDecimal("0.8");

    @Override
    public BigDecimal calculateFreight(Shipment shipment) {
        // Simulate market fluctuation
        BigDecimal marketMultiplier = new BigDecimal("0.8").add(BigDecimal.valueOf(Math.random() * 0.5)); //0.8 to 1.3
        BigDecimal weightCost = null;
        BigDecimal volumeCost = null;
        return weightCost.add(volumeCost).multiply(marketMultiplier);
    }

    @Override
    public String getTransportationType() {
        return "Boat";
    }
}
