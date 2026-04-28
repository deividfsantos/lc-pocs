package com.dsantos.validation.validators;

import com.dsantos.validation.constraints.Size;
import com.dsantos.validation.core.ConstraintValidator;

import java.util.Collection;

public class SizeValidator implements ConstraintValidator<Size, Object> {

    private int min;
    private int max;

    @Override
    public void initialize(Size annotation) {
        this.min = annotation.min();
        this.max = annotation.max();
    }

    @Override
    public boolean isValid(Object value) {
        if (value == null) return true;
        int size = switch (value) {
            case String s -> s.length();
            case Collection<?> c -> c.size();
            default -> -1;
        };
        if (size < 0) return true;
        return size >= min && size <= max;
    }
}
