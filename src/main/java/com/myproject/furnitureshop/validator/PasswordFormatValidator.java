package com.myproject.furnitureshop.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;


public class PasswordFormatValidator implements ConstraintValidator<PasswordFormatConstraint, String> {
    @Value("${user.password.regexp}")
    private String passwordRegex;

    @Override
    public void initialize(PasswordFormatConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null || s.isEmpty()) return true;
        return s.matches(passwordRegex);
    }
}
