package com.myproject.furnitureshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PermissionCreationRequest(
        @NotNull(message = "PERMISSION_NAME_REQUIRED")
        @NotBlank(message = "PERMISSION_NAME_REQUIRED")
        String name,

        @NotNull(message = "PERMISSION_DESC_REQUIRED")
        @NotBlank(message = "PERMISSION_DESC_REQUIRED")
        String description
) {
}
