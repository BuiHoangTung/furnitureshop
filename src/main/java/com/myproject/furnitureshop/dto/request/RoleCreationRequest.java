package com.myproject.furnitureshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record RoleCreationRequest(
        @NotNull(message = "ROLE_NAME_REQUIRED")
        @NotBlank(message = "ROLE_NAME_REQUIRED")
        String name,

        @NotNull(message = "ROLE_DESC_REQUIRED")
        @NotBlank(message = "ROLE_DESC_REQUIRED")
        String description,

        Set<String> permissionList
) {
}
