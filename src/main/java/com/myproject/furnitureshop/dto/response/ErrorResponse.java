package com.myproject.furnitureshop.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ErrorResponse<T> extends BaseResponse {
    private final String errorCode;
    private final T errors;

    public static <T> ErrorResponse<T> of(String message, String errorCode, T errors) {
        return ErrorResponse.<T>builder()
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .errors(errors)
                .build();
    }
}
