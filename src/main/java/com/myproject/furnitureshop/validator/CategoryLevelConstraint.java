package com.myproject.furnitureshop.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {CategoryLevelValidator.class}
)
public @interface CategoryLevelConstraint {
    String message() default "CAT_INVALID_LEVEL";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
