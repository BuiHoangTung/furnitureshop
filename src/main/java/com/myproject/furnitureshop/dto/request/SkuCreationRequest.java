package com.myproject.furnitureshop.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Set;

public record SkuCreationRequest(
        @NotBlank(message = "SKU_TITLE_REQUIRED")
        @Size(min = 3, max = 255, message = "SKU_TITLE_SIZE")
        String title,

        @NotNull(message = "SKU_PRICE_REQUIRED")
        @Digits(integer = 13, fraction = 2, message = "SKU_PRICE_FORMAT")
        @DecimalMin(value = "100000.00", message = "SKU_PRICE_MIN")
        BigDecimal price,

        @NotNull(message = "SKU_QUANTITY_REQUIRED")
        @Min(value = 1, message = "SKU_QUANTITY_MIN")
        Integer quantity,

        @NotBlank(message = "SKU_DESC_REQUIRED")
        @Size(min = 100, max = 500, message = "SKU_DESC_LENGTH")
        String description,

        @NotBlank(message = "SKU_DETAIL_DESC_REQUIRED")
        @Size(min = 200, max = 1500, message = "SKU_DETAIL_DESC_LENGTH")
        String detailDescription,

        @NotEmpty(message = "SKU_OPT_VAL_REQUIRED")
        @Size(min = 1, max = 3, message = "SKU_OPT_VAL_SIZE")
        @Valid
        Set<OptionValueCreationRequest> optionValues,

        @NotNull(message = "SKU_WEIGHT_REQUIRED")
        @Digits(integer = 8, fraction = 2, message = "SKU_WEIGHT_FORMAT")
        @DecimalMin(value = "0.00", inclusive = false, message = "SKU_WEIGHT_MIN")
        BigDecimal weight,

        @NotNull(message = "SKU_WIDTH_REQUIRED")
        @Digits(integer = 8, fraction = 2, message = "SKU_WIDTH_FORMAT")
        @DecimalMin(value = "0.00", inclusive = false, message = "SKU_WIDTH_MIN")
        BigDecimal width,

        @NotNull(message = "SKU_DEPTH_REQUIRED")
        @Digits(integer = 8, fraction = 2, message = "SKU_DEPTH_FORMAT")
        @DecimalMin(value = "0.00", inclusive = false, message = "SKU_DEPTH_MIN")
        BigDecimal depth,

        @NotNull(message = "SKU_HEIGHT_REQUIRED")
        @Digits(integer = 8, fraction = 2, message = "SKU_HEIGHT_FORMAT")
        @DecimalMin(value = "0.00", inclusive = false, message = "SKU_HEIGHT_MIN")
        BigDecimal height,

        @NotEmpty(message = "SKU_SPECIFICATION_REQUIRED")
        @Size(min = 1, max = 20, message = "SKU_SPECIFICATION_SIZE")
        @Valid
        Set<SpecificationCreationRequest> specifications
) {}
