package com.dsantos.transportation;

import com.dsantos.Shipment;

import java.math.BigDecimal;

public interface TransportationStrategy {

    BigDecimal calculateFreight(Shipment shipment);

    String getTransportationType();
}
