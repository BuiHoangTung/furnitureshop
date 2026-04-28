package com.myproject.furnitureshop.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SuccessResponse<T> extends BaseResponse {
    private final T result;

    public static <T> SuccessResponse<T> of(String message, T result) {
        return SuccessResponse.<T>builder()
                .success(true)
                .message(message)
                .result(result)
                .build();
    }
}
