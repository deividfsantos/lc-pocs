package com.dsantos.validation.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ValidationResult {

    private final List<ConstraintViolation> violations;

    private ValidationResult(List<ConstraintViolation> violations) {
        this.violations = Collections.unmodifiableList(violations);
    }

    public static ValidationResult valid() {
        return new ValidationResult(new ArrayList<>());
    }

    public static ValidationResult invalid(List<ConstraintViolation> violations) {
        return new ValidationResult(violations);
    }

    public boolean isValid() {
        return violations.isEmpty();
    }

    public List<ConstraintViolation> getViolations() {
        return violations;
    }

    @Override
    public String toString() {
        return isValid() ? "ValidationResult{valid}" : "ValidationResult{violations=" + violations + "}";
    }
}
