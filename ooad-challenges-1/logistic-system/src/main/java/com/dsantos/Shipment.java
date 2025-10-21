package com.dsantos;

import java.math.BigDecimal;

public record Shipment(BigDecimal volume, BigDecimal weight, String destination) {
}
