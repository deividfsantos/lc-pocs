package com.dsantos.validation.validators;

import com.dsantos.validation.constraints.NotBlank;
import com.dsantos.validation.core.ConstraintValidator;

public class NotBlankValidator implements ConstraintValidator<NotBlank, String> {

    @Override
    public void initialize(NotBlank annotation) {}

    @Override
    public boolean isValid(String value) {
        return value != null && !value.isBlank();
    }
}
