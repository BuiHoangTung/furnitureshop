package com.myproject.furnitureshop.dto.request;

import com.myproject.furnitureshop.validator.CategoryLevelConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryCreationRequest(
        @NotNull(message = "CATEGORY_NAME_REQUIRED")
        @NotBlank(message = "CATEGORY_NAME_REQUIRED")
        String name,

        @NotNull(message = "CATEGORY_LEVEL_REQUIRED")
        @NotBlank(message = "CATEGORY_LEVEL_REQUIRED")
        @CategoryLevelConstraint
        String level,

        String parentName
) {
}
