package com.myproject.furnitureshop.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION("SYS-UNCAT-EXC", "Uncategorized error.", HttpStatus.BAD_REQUEST),
    INVALID_KEY_VALIDATION_EXCEPTION("SYS-VAL-KEY", "Invalid error field message key.", HttpStatus.INTERNAL_SERVER_ERROR),

    /* Validation */
    VALIDATION_ERROR("VAL-BUS-001", "Validation failed.", HttpStatus.BAD_REQUEST),

    /* User domain exception */
    USER_ALREADY_EXISTS("USR-BUS-001", "Account already exists.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("USR-BUS-002", "User is not exist.", HttpStatus.BAD_REQUEST),

    IDENTIFIER_REQUIRED("USR-VAL-001", "Identifier is mandatory.", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL_FORMAT("USR-VAL-002", "Email is invalid.", HttpStatus.BAD_REQUEST),
    PASSWORD_REQUIRED("USR-VAL-003", "Password is mandatory.", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_FORMAT("USR-VAL-004", "Password is invalid.", HttpStatus.BAD_REQUEST),
    CONFIRM_PASSWORD_REQUIRED("USR-VAL-005", "Confirm password is mandatory.", HttpStatus.BAD_REQUEST),
    PASSWORD_CONFIRMATION_MISMATCH("USR-VAL-006", "Password and confirmation do not match.", HttpStatus.BAD_REQUEST),

    /* Role domain exception */
    ROLE_NOT_FOUND("ROLE-BUS-001", "Role is not exist.", HttpStatus.BAD_REQUEST),

    /* Permission domain exception */
    PERMISSION_NAME_REQUIRED("PERMN-VAL-001", "Permission name is mandatory.", HttpStatus.BAD_REQUEST),
    PERMISSION_DESC_REQUIRED("PERMN-VAL-002", "Permission description is mandatory.", HttpStatus.BAD_REQUEST),

    PERMISSION_NOT_FOUND("PERMN-BUS-001", "Permission is not exist.", HttpStatus.BAD_REQUEST),
    PERMISSION_ALREADY_INACTIVE("PERMN-BUS-002", "Permission already inactive.", HttpStatus.BAD_REQUEST),
    PERMISSION_ALREADY_EXISTS("PERMN-BUS-003", "Permission already exists.", HttpStatus.BAD_REQUEST),
    PERMISSION_ALREADY_ACTIVE("PERMN-BUS-004", "Permission already active.", HttpStatus.BAD_REQUEST),

    /* Role domain exception */
    ROLE_ALREADY_EXISTS("ROLE-BUS-001", "Role already exists.", HttpStatus.BAD_REQUEST),

    ROLE_NAME_REQUIRED("ROLE-VAL-001", "Role name is mandatory.", HttpStatus.BAD_REQUEST),
    ROLE_DESC_REQUIRED("ROLE-VAL-002", "Role description is mandatory.", HttpStatus.BAD_REQUEST),
    ROLE_ID_REQUIRED("ROLE-VAL-003", "Role ID is mandatory.", HttpStatus.BAD_REQUEST),

    /* Authentication domain - Jwt token domain*/
    AUTHENTICATION_FAILED("AUTH-BUS-001", "Invalid email or password.", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED("AUTH-BUS-002", "Token has expired.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("AUTH-BUS-003", "Invalid token.", HttpStatus.UNAUTHORIZED),
    AUTHENTICATION_REQUIRED("AUTH-BUS-004", "Authentication is required to access this resource.", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED("AUTH-BUS-005", "You do not have permission to perform this action.", HttpStatus.FORBIDDEN),
    TOKEN_REVOKED("AUTH-BUS-006", "Token already revoked.", HttpStatus.BAD_REQUEST),

    JWT_REFRESH_TOKEN_REQUIRED("JWT-VAL-001", "Refresh token is mandatory.", HttpStatus.BAD_REQUEST),
    JWT_TOKEN_REQUIRED("JWT-VAL-002", "Access token is mandatory.", HttpStatus.BAD_REQUEST),

    INVALID_REFRESH_TOKEN("JWT-BUS-001", "Refresh token is invalid.", HttpStatus.UNAUTHORIZED),

    /* Otp domain exception */
    OTP_INVALID_TOKEN("OTP-BUS-001", "Incorrect otp or your otp is expiry.", HttpStatus.BAD_REQUEST),
    OTP_SESSION_EXPIRED("OTP-BUS-002", "OTP session expired.", HttpStatus.BAD_REQUEST),
    OTP_RESEND_FAILED("OTP-BUS-003", "Resend OTP limit exceeded.", HttpStatus.BAD_REQUEST),
    OTP_RESEND_NOT_ALLOWED("OTP-BUS-004", "Resend OTP is not allowed at this time. Please try again later.", HttpStatus.BAD_REQUEST),
    OTP_ALREADY_SENT("OTP-BUS-005", "OTP already sent.", HttpStatus.BAD_REQUEST),
    OTP_SEND_FAILED("OTP-SYS-006", "Failed to send OTP. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR),
    OTP_VERIFICATION_INVALID("OTP-SYS-007", "OTP verification is invalid or expired.", HttpStatus.BAD_REQUEST),

    /* External API failures, payment gateway, 3rd-party service issues */
    TURNSTILE_INVALID_TOKEN("TURNSTILE-INT-001", "Token timeout or duplicate", HttpStatus.BAD_REQUEST)

    ;
    ErrorCode(String errorCode, String message, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
