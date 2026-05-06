package com.myproject.furnitureshop.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WhiteSpaceValidator implements ConstraintValidator<WhiteSpaceConstraint, String> {
    @Override
    public void initialize(WhiteSpaceConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null || s.isBlank()) return true;

        return s.equals(s.trim());
    }
}
