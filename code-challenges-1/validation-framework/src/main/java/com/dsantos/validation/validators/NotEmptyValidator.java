package com.dsantos.validation.validators;

import com.dsantos.validation.constraints.NotEmpty;
import com.dsantos.validation.core.ConstraintValidator;

import java.util.Collection;

public class NotEmptyValidator implements ConstraintValidator<NotEmpty, Object> {

    @Override
    public void initialize(NotEmpty annotation) {}

    @Override
    public boolean isValid(Object value) {
        if (value == null) return false;
        if (value instanceof String s) return !s.isEmpty();
        if (value instanceof Collection<?> c) return !c.isEmpty();
        return true;
    }
}
