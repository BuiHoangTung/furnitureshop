package com.myproject.furnitureshop.dto.request;

import com.myproject.furnitureshop.validator.ConfirmPasswordConstraint;
import com.myproject.furnitureshop.validator.PasswordFormatConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ConfirmPasswordConstraint
public record CreatePasswordRequest(
        String uuid,

        @NotNull(message = "PASSWORD_REQUIRED")
        @NotBlank(message = "PASSWORD_REQUIRED")
//        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$", message = "INVALID_PASSWORD_FORMAT")
        @PasswordFormatConstraint
        String password,

        @NotNull(message = "CONFIRM_PASSWORD_REQUIRED")
        @NotBlank(message = "CONFIRM_PASSWORD_REQUIRED")
        String confirmPassword
) {
}
