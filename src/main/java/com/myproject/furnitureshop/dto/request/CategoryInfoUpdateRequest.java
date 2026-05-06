package com.myproject.furnitureshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryInfoUpdateRequest(
        @NotNull(message = "CATEGORY_NAME_REQUIRED")
        @NotBlank(message = "CATEGORY_NAME_REQUIRED")
        String name,

        @NotNull(message = "CAT_PARENT_REQUIRED")
        @NotBlank(message = "CAT_PARENT_REQUIRED")
        String parentName
) {
}
