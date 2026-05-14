package com.myproject.furnitureshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ShopOptionCreationRequest(
        @NotBlank(message = "OPT_NAME_REQUIRED")
        @Size(min = 3, max = 100, message = "OPT_NAME_SIZE")
        String name
) {
}
