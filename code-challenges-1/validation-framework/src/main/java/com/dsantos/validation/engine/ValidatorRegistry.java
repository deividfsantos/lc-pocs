package com.dsantos.validation.engine;

import com.dsantos.validation.constraints.Email;
import com.dsantos.validation.constraints.Max;
import com.dsantos.validation.constraints.Min;
import com.dsantos.validation.constraints.NotBlank;
import com.dsantos.validation.constraints.NotEmpty;
import com.dsantos.validation.constraints.NotNull;
import com.dsantos.validation.constraints.Pattern;
import com.dsantos.validation.constraints.Size;
import com.dsantos.validation.core.ConstraintValidator;
import com.dsantos.validation.validators.EmailValidator;
import com.dsantos.validation.validators.MaxValidator;
import com.dsantos.validation.validators.MinValidator;
import com.dsantos.validation.validators.NotBlankValidator;
import com.dsantos.validation.validators.NotEmptyValidator;
import com.dsantos.validation.validators.NotNullValidator;
import com.dsantos.validation.validators.PatternValidator;
import com.dsantos.validation.validators.SizeValidator;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class ValidatorRegistry {

    private final Map<Class<? extends Annotation>, ConstraintValidator<?, ?>> validators = new HashMap<>();

    public ValidatorRegistry() {
        register(NotNull.class, new NotNullValidator())
                .register(NotEmpty.class, new NotEmptyValidator())
                .register(NotBlank.class, new NotBlankValidator())
                .register(Min.class, new MinValidator())
                .register(Max.class, new MaxValidator())
                .register(Size.class, new SizeValidator())
                .register(Pattern.class, new PatternValidator())
                .register(Email.class, new EmailValidator());
    }

    public <A extends Annotation> ValidatorRegistry register(Class<A> annotationType,
                                                              ConstraintValidator<A, ?> validator) {
        validators.put(annotationType, validator);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <A extends Annotation, T> ConstraintValidator<A, T> getValidator(Class<A> annotationType) {
        return (ConstraintValidator<A, T>) validators.get(annotationType);
    }

    public boolean supports(Class<? extends Annotation> annotationType) {
        return validators.containsKey(annotationType);
    }
}
