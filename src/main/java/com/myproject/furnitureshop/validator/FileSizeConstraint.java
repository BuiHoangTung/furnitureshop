package com.myproject.furnitureshop.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {FileSizeValidator.class}
)
public @interface FileSizeConstraint {
    String message() default "INVALID_FILE_SIZE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long max();
}
