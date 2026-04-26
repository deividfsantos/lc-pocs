package com.dsantos.validation.core;

public record ConstraintViolation(String field, String message, Object invalidValue) {

    @Override
    public String toString() {
        return "ConstraintViolation{field='" + field + "', message='" + message + "', value=" + invalidValue + "}";
    }
}
