package com.myproject.furnitureshop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseResponse {
    @Builder.Default
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final boolean success;
    private final String message;
}
