package com.myproject.furnitureshop.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record OtpVerificationRequest(
        @NotNull(message = "OTP_INVALID_TOKEN")
        @NotBlank(message = "OTP_INVALID_TOKEN")
        @Pattern(regexp = "^[0-9]{6}$", message = "OTP_INVALID_TOKEN")
        String otp,

        @NotNull(message = "IDENTIFIER_REQUIRED")
        @NotBlank(message = "IDENTIFIER_REQUIRED")
        @Email(message = "INVALID_EMAIL_FORMAT")
        String identifier
) {
}
