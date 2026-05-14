package com.myproject.furnitureshop.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ProductCreationRequest(
        @NotNull(message = "CAT_IDENTIFIER_REQUIRED")
        Long idCategory,

        @NotBlank(message = "PRODUCT_NAME_REQUIRED")
        @Size(min = 10, max = 100, message = "PRODUCT_NAME_LENGTH")
        String name,

        @NotEmpty(message = "PRODUCT_SKUS_REQUIRED")
        @Size(min = 1, max = 5, message = "PRODUCT_SKUS_SIZE")
        @Valid
        List<SkuCreationRequest> skus
) {}
