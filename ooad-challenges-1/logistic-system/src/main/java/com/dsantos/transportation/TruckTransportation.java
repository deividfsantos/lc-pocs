package com.dsantos.transportation;

import com.dsantos.Shipment;

import java.math.BigDecimal;

public class TruckTransportation implements TransportationStrategy {

    private static final BigDecimal BASE_RATE = new BigDecimal("2.5");
    private static final BigDecimal VOLUME_RATE = new BigDecimal("1.8");

    @Override
    public BigDecimal calculateFreight(Shipment shipment) {
        // Simulate market fluctuation
        BigDecimal marketMultiplier = new BigDecimal("0.9").add(BigDecimal.valueOf(Math.random() * 0.4)); //0.9 to 1.3
        BigDecimal weightCost = shipment.weight().multiply(BASE_RATE);
        BigDecimal volumeCost = shipment.volume().multiply(VOLUME_RATE);
        return weightCost.add(volumeCost).multiply(marketMultiplier);
    }

    @Override
    public String getTransportationType() {
        return "Truck";
    }
}
