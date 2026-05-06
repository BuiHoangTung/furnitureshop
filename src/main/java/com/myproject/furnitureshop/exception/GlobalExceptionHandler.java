package com.myproject.furnitureshop.exception;

import com.myproject.furnitureshop.dto.response.BaseResponse;
import com.myproject.furnitureshop.dto.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleUncategorizedException(Exception exception) {
        log.error(exception.getMessage(), exception);

        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;

        ErrorResponse<String> errorResponse = ErrorResponse.of(exception.getMessage(), errorCode.getErrorCode(), null);

        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<BaseResponse> handleAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        BaseResponse response = ErrorResponse.of(errorCode.getMessage(), errorCode.getErrorCode(), null);

        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> enumKeyMap = new HashMap<>();
        ErrorCode validationErrorCode = ErrorCode.VALIDATION_ERROR;

        exception.getFieldErrors().forEach((fieldError -> {
            String fieldName = fieldError.getField();
            ErrorCode errorCode = ErrorCode.INVALID_KEY_VALIDATION_EXCEPTION;

            try {
                errorCode = ErrorCode.valueOf(fieldError.getDefaultMessage());
            } catch(IllegalArgumentException e) {

            }

            String errMsg = errorCode.getMessage();

            enumKeyMap.put(fieldName, errMsg);
        }));

        BaseResponse response = ErrorResponse.of(validationErrorCode.getMessage(), validationErrorCode.getErrorCode(), enumKeyMap);

        return ResponseEntity.status(validationErrorCode.getHttpStatus()).body(response);
    }

    @ExceptionHandler(value = JwtException.class)
    public ResponseEntity<?> handleJwtExceptions(JwtException exception) {
        ErrorCode errorCode =
            switch (exception) {
                case ExpiredJwtException e -> ErrorCode.TOKEN_EXPIRED;
                case SignatureException e -> ErrorCode.INVALID_TOKEN;
                case MalformedJwtException e -> ErrorCode.INVALID_TOKEN;
                default -> ErrorCode.UNCATEGORIZED_EXCEPTION;
            };

        ErrorResponse<String> response = ErrorResponse.of(errorCode.getMessage(), errorCode.getErrorCode(), null);

        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }

    @ExceptionHandler(value = AuthorizationDeniedException.class)
    public ResponseEntity<?> handleAuthorizationDeniedException(AuthorizationDeniedException exception) {
        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;

        ErrorResponse<String> response = ErrorResponse.of(errorCode.getMessage(), errorCode.getErrorCode(), null);

        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }
}
