package com.dsantos.validation.validators;

import com.dsantos.validation.constraints.Min;
import com.dsantos.validation.core.ConstraintValidator;

public class MinValidator implements ConstraintValidator<Min, Number> {

    private long minValue;

    @Override
    public void initialize(Min annotation) {
        this.minValue = annotation.value();
    }

    @Override
    public boolean isValid(Number value) {
        if (value == null) return true;
        return value.longValue() >= minValue;
    }
}
