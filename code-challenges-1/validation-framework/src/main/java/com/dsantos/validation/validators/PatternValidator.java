package com.dsantos.validation.validators;

import com.dsantos.validation.constraints.Pattern;
import com.dsantos.validation.core.ConstraintValidator;

public class PatternValidator implements ConstraintValidator<Pattern, String> {

    private java.util.regex.Pattern compiled;

    @Override
    public void initialize(Pattern annotation) {
        this.compiled = java.util.regex.Pattern.compile(annotation.regexp());
    }

    @Override
    public boolean isValid(String value) {
        if (value == null) return true;
        return compiled.matcher(value).matches();
    }
}
