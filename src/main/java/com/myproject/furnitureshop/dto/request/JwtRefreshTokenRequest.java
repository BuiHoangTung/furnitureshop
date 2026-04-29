package com.myproject.furnitureshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record JwtRefreshTokenRequest(
        @NotNull(message = "JWT_REFRESH_TOKEN_REQUIRED")
        @NotBlank(message = "JWT_REFRESH_TOKEN_REQUIRED")
        String urt
) {
}
