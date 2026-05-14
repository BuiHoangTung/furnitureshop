package com.myproject.furnitureshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public record SpecificationCreationRequest(
        @NotBlank(message = "SPE_NAME_REQUIRED")
        @Size(message = "SPE_NAME_SIZE")
        String name,

        @NotBlank(message = "SPE_VALUE_REQUIRED")
        @Size(message = "SPE_VALUE_SIZE")
        String value
) {
    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        SpecificationCreationRequest that = (SpecificationCreationRequest) object;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
