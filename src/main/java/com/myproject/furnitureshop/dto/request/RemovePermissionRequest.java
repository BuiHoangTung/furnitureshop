package com.myproject.furnitureshop.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record RemovePermissionRequest(
        @NotNull(message = "ROLE_ID_REQUIRED")
        Long id,
        Set<String> removePermissionList
) {
}
