package com.dsantos;

import java.math.BigDecimal;

public enum Zone {
    VIP(BigDecimal.valueOf(150)),
    PREMIUM(BigDecimal.valueOf(100)),
    REGULAR(BigDecimal.valueOf(50)),
    ECONOMY(BigDecimal.valueOf(30));

    private final BigDecimal price;

    Zone(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
