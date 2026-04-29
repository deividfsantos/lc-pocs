package com.dsantos.validation.validators;

import com.dsantos.validation.constraints.Email;
import com.dsantos.validation.core.ConstraintValidator;

import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email, String> {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$");

    @Override
    public void initialize(Email annotation) {}

    @Override
    public boolean isValid(String value) {
        if (value == null) return true;
        return EMAIL_PATTERN.matcher(value).matches();
    }
}
