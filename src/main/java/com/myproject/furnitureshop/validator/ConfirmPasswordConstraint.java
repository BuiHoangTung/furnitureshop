package com.myproject.furnitureshop.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {ConfirmPasswordValidator.class}
)
public @interface ConfirmPasswordConstraint {
    String message() default "PASSWORD_CONFIRMATION_MISMATCH";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
