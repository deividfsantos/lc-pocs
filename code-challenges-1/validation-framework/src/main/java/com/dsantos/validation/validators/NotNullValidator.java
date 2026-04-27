package com.dsantos.validation.validators;

import com.dsantos.validation.constraints.NotNull;
import com.dsantos.validation.core.ConstraintValidator;

public class NotNullValidator implements ConstraintValidator<NotNull, Object> {

    @Override
    public void initialize(NotNull annotation) {}

    @Override
    public boolean isValid(Object value) {
        return value != null;
    }
}
