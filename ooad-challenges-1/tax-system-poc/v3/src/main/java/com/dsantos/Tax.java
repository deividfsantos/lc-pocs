package com.dsantos;

import java.math.BigDecimal;

public class Tax {

    private final String state;
    private final Integer year;
    private final String product;
    private final BigDecimal tax;

    public Tax(String state, Integer year, String product, BigDecimal tax) {
        this.state = state;
        this.year = year;
        this.product = product;
        this.tax = tax;
    }

    public String getState() {
        return state;
    }

    public Integer getYear() {
        return year;
    }

    public String getProduct() {
        return product;
    }

    public BigDecimal getTax() {
        return tax;
    }
}
