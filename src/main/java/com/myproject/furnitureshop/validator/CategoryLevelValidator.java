package com.myproject.furnitureshop.validator;

import com.myproject.furnitureshop.enums.CategoryLevel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class CategoryLevelValidator implements ConstraintValidator<CategoryLevelConstraint, String> {
    @Override
    public void initialize(CategoryLevelConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null || s.isBlank()) return false;

        return Arrays.stream(CategoryLevel.values())
                .anyMatch(e -> e.name().equals(s));
    }
}
