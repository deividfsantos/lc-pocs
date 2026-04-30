package com.dsantos.validation.engine;

import com.dsantos.validation.constraints.Constraint;
import com.dsantos.validation.constraints.Valid;
import com.dsantos.validation.core.ConstraintValidator;
import com.dsantos.validation.core.ConstraintViolation;
import com.dsantos.validation.core.ValidationResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ValidationEngine {

    private final ValidatorRegistry registry;

    public ValidationEngine() {
        this.registry = new ValidatorRegistry();
    }

    public ValidationEngine(ValidatorRegistry registry) {
        this.registry = registry;
    }

    public ValidationResult validate(Object object) {
        if (object == null) {
            return ValidationResult.invalid(List.of(
                    new ConstraintViolation("object", "must not be null", null)));
        }

        List<ConstraintViolation> violations = new ArrayList<>();
        collectViolations(object, "", violations);

        return violations.isEmpty() ? ValidationResult.valid() : ValidationResult.invalid(violations);
    }

    private void collectViolations(Object object, String prefix, List<ConstraintViolation> violations) {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                continue;
            }

            String fieldPath = prefix.isEmpty() ? field.getName() : prefix + "." + field.getName();

            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof Valid) {
                    if (value != null) {
                        collectViolations(value, fieldPath, violations);
                    }
                } else {
                    processAnnotation(fieldPath, annotation, value, violations);
                }
            }
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void processAnnotation(String fieldPath, Annotation annotation, Object value,
                                   List<ConstraintViolation> violations) {
        Class<? extends Annotation> type = annotation.annotationType();

        if (registry.supports(type)) {
            ConstraintValidator validator = registry.getValidator(type);
            validator.initialize(annotation);
            if (!validator.isValid(value)) {
                violations.add(new ConstraintViolation(fieldPath, resolveMessage(annotation, type), value));
            }
            return;
        }

        Constraint constraint = type.getAnnotation(Constraint.class);
        if (constraint != null) {
            for (Class<? extends ConstraintValidator<?, ?>> validatorClass : constraint.validatedBy()) {
                try {
                    ConstraintValidator validator = validatorClass.getDeclaredConstructor().newInstance();
                    validator.initialize(annotation);
                    if (!validator.isValid(value)) {
                        violations.add(new ConstraintViolation(fieldPath, resolveMessage(annotation, type), value));
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Failed to create validator: " + validatorClass.getName(), e);
                }
            }
        }
    }

    private String resolveMessage(Annotation annotation, Class<? extends Annotation> type) {
        try {
            return (String) type.getMethod("message").invoke(annotation);
        } catch (Exception e) {
            return "invalid value";
        }
    }

    public ValidatorRegistry getRegistry() {
        return registry;
    }
}
