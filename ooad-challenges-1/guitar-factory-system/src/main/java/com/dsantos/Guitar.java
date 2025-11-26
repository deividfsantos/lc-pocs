package com.dsantos;

import java.math.BigDecimal;

public record Guitar(String serialNumber, BigDecimal price, GuitarSpec spec) {
}
