package com.dsantos.validation.engine;

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

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                continue;
            }

            for (Annotation annotation : field.getAnnotations()) {
                processAnnotation(field.getName(), annotation, value, violations);
            }
        }

        return violations.isEmpty() ? ValidationResult.valid() : ValidationResult.invalid(violations);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void processAnnotation(String fieldName, Annotation annotation, Object value,
                                   List<ConstraintViolation> violations) {
        Class<? extends Annotation> type = annotation.annotationType();
        if (!registry.supports(type)) return;

        ConstraintValidator validator = registry.getValidator(type);
        validator.initialize(annotation);

        if (!validator.isValid(value)) {
            String message = resolveMessage(annotation, type);
            violations.add(new ConstraintViolation(fieldName, message, value));
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
