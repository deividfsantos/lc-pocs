package com.dsantos.validation.validators;

import com.dsantos.validation.constraints.Max;
import com.dsantos.validation.core.ConstraintValidator;

public class MaxValidator implements ConstraintValidator<Max, Number> {

    private long maxValue;

    @Override
    public void initialize(Max annotation) {
        this.maxValue = annotation.value();
    }

    @Override
    public boolean isValid(Number value) {
        if (value == null) return true;
        return value.longValue() <= maxValue;
    }
}
