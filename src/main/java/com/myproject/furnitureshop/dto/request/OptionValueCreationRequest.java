package com.myproject.furnitureshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public record OptionValueCreationRequest(
        @NotBlank(message = "OPT_NAME_REQUIRED")
        @Size(min = 3, max = 100, message = "OPT_NAME_SIZE")
        String name,

        @NotBlank(message = "OPT_VALUE_REQUIRED")
        @Size(min = 3, max = 50, message = "OPT_VALUE_SIZE")
        String value
) {
    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        OptionValueCreationRequest that = (OptionValueCreationRequest) object;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
