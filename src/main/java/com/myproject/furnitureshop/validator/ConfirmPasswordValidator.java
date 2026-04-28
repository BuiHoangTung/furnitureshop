package com.myproject.furnitureshop.validator;

import com.myproject.furnitureshop.dto.request.CreatePasswordRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPasswordConstraint, CreatePasswordRequest> {
    @Override
    public void initialize(ConfirmPasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CreatePasswordRequest createPasswordRequest, ConstraintValidatorContext constraintValidatorContext) {
        String password = createPasswordRequest.password();
        String confirmPassword = createPasswordRequest.confirmPassword();

        if(password == null || confirmPassword == null) return true;

        boolean isValid = confirmPassword.equals(password);

        if(!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();

            constraintValidatorContext.buildConstraintViolationWithTemplate("PASSWORD_CONFIRMATION_MISMATCH")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }

        return isValid;
    }
}
