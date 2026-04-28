package com.myproject.furnitureshop.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SendOtpRequest(
        @NotNull(message = "IDENTIFIER_REQUIRED")
        @NotBlank(message = "IDENTIFIER_REQUIRED")
        @Email(message = "INVALID_EMAIL_FORMAT")
        String identifier,
        String turnStileToken
) {
}
