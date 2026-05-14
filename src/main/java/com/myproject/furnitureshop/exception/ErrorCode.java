package com.myproject.furnitureshop.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION("SYS-UNCAT-EXC", "Uncategorized error.", HttpStatus.BAD_REQUEST),
    INVALID_KEY_VALIDATION_EXCEPTION("SYS-VAL-KEY", "Invalid error field message key.", HttpStatus.INTERNAL_SERVER_ERROR),

    FIELD_NO_REDUNDANCY_WHITE_SPACE("FIELD-VAL-001", "Must not contain leading or trailing whitespace.", HttpStatus.BAD_REQUEST),
    FIELD_NO_BLANK("FIELD-VAL-002", "This field can not be blank.", HttpStatus.BAD_REQUEST),
    FIELD_INVALID_FORMAT("FIELD-VAL-003", "Field format is invalid.", HttpStatus.BAD_REQUEST),

    /* Rate limit */
    MANY_REQUEST("RATE-LIM-BUS-001", "Rate limit exceeded for this endpoint", HttpStatus.TOO_MANY_REQUESTS),

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
    USER_ALREADY_LOGGED_IN("AUTH-BUS-007", "User is already logged in.", HttpStatus.BAD_REQUEST),

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

    /* Category domain exception */
    CAT_PARENT_REQUIRED("CAT-BUS-001", "Category parent is mandatory.", HttpStatus.BAD_REQUEST),
    CAT_NOT_FOUND("CAT-BUS-002", "Category not found.", HttpStatus.BAD_REQUEST),
    CAT_INVALID_PARENT_LEVEL("CAT-BUS-003", "Invalid parent category level.", HttpStatus.BAD_REQUEST),
    CAT_ALREADY_EXISTS("CAT-BUS-004", "Category already exists.", HttpStatus.BAD_REQUEST),
    CAT_PARENT_NOT_FOUND("CAT-BUS-005", "Category parent not found.", HttpStatus.BAD_REQUEST),
    CAT_ONLY_ACTIVE("CAT-BUS-006", "Operation allowed only when category status is ACTIVE.", HttpStatus.CONFLICT),
    CAT_ALREADY_DELETED("CAT-BUS-007", "Categories already deleted.", HttpStatus.BAD_REQUEST),

    CATEGORY_NAME_REQUIRED("CAT-VAL-001", "Name of category is mandatory.", HttpStatus.BAD_REQUEST),
    CATEGORY_LEVEL_REQUIRED("CAT-VAL-002", "Level of category is mandatory.", HttpStatus.BAD_REQUEST),
    CAT_INVALID_LEVEL("CAT-VAL-003", "Invalid level.", HttpStatus.BAD_REQUEST),
    CAT_IDENTIFIER_REQUIRED("CAT-VAL-004", "Category identifier is mandatory.", HttpStatus.BAD_REQUEST),

    /* File domain exception */
    FILE_VIRUS_DETECTED("FILE-BUS-001", "Virus detected.", HttpStatus.BAD_REQUEST),
    INVALID_FILE_PATH("FILE-BUS-002", "Invalid file path.", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_STORAGE_ERROR("FILE-BUS-003", "Failed to store file", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_NOT_FOUND("FILE-BUS-004", "File not found.", HttpStatus.BAD_REQUEST),
    INVALID_URL_FILE("FILE-BUS-005", "Invalid file URL format.", HttpStatus.BAD_REQUEST),
    FILE_ACCESS_DENIED("FILE-BUS-006", "You do not have permission to access this file.", HttpStatus.FORBIDDEN),
    FILE_DELETE_FAILED("FILE-BUS-007", "Failed to delete file due to internal error.", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_READ_ERROR("FILE-BUS-008", "Failed to read file.", HttpStatus.INTERNAL_SERVER_ERROR),

    INVALID_FILE_TYPE("FILE-VAL-001", "Invalid file type.", HttpStatus.BAD_REQUEST),
    INVALID_FILE_SIZE("FILE-VAL-002", "Invalid file size.", HttpStatus.BAD_REQUEST),
    FILE_REQUIRED("FILE-VAL-003", "File upload can not be null.", HttpStatus.BAD_REQUEST),

    /* Product domain exception */
    PRODUCT_NAME_REQUIRED("PROD-VAL-001", "Name of product is mandatory.", HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_LENGTH("PROD-VAL-002", "Product name must be between 10 and 100 characters.", HttpStatus.BAD_REQUEST),
    PRODUCT_SKUS_SIZE("PROD-VAL-003", "Product must contain between 1 and 5 SKUs.", HttpStatus.BAD_REQUEST),
    PRODUCT_SKUS_REQUIRED("PROD-VAL-004", "Product must contain at least one SKU.", HttpStatus.BAD_REQUEST),

    /* Sku domain exception */
    SKU_TITLE_REQUIRED("SKU-VAL-001", "Title of sku is mandatory.", HttpStatus.BAD_REQUEST),
    SKU_TITLE_SIZE("SKU-VAL-002", "Title of sku must be between 3 and 255 characters.", HttpStatus.BAD_REQUEST),
    SKU_PRICE_REQUIRED("SKU-VAL-003", "Price of sku is mandatory.", HttpStatus.BAD_REQUEST),
    SKU_PRICE_MIN("SKU-VAL-004", "Price of sku must be at least 100.000VNĐ", HttpStatus.BAD_REQUEST),
    SKU_PRICE_FORMAT("SKU-VAL-005", "Price must contain up to 13 integer digits and 2 decimal places.", HttpStatus.BAD_REQUEST),
    SKU_QUANTITY_REQUIRED("SKU-VAL-006", "Sku quantity is mandatory.", HttpStatus.BAD_REQUEST),
    SKU_QUANTITY_MIN("SKU-VAL-007", "Sku quantity must be at least 1.", HttpStatus.BAD_REQUEST),
    SKU_DESC_REQUIRED("SKU-VAL-008", "Sku description is mandatory.", HttpStatus.BAD_REQUEST),
    SKU_DETAIL_DESC_REQUIRED("SKU-VAL-009", "Sku detail description is mandatory.", HttpStatus.BAD_REQUEST),
    SKU_DESC_LENGTH("SKU-VAL-010", "Sku description must be between 100 and 500 characters.", HttpStatus.BAD_REQUEST),
    SKU_DETAIL_DESC_LENGTH("SKU-VAL-011", "Sku detail description must be between 200 and 1500 characters.", HttpStatus.BAD_REQUEST),
    SKU_OPT_VAL_REQUIRED("SKU-VAL-012", "List of Option value of sku is mandatory.", HttpStatus.BAD_REQUEST),
    SKU_OPT_VAL_SIZE("SKU-VAL-013", "List of Option value of sku must be between 1 and 3.", HttpStatus.BAD_REQUEST),
    SKU_WEIGHT_REQUIRED("SKU-VAL-014", "Weight of sku is mandatory.", HttpStatus.BAD_REQUEST),
    SKU_WIDTH_REQUIRED("SKU-VAL-015", "Width of sku is mandatory.", HttpStatus.BAD_REQUEST),
    SKU_HEIGHT_REQUIRED("SKU-VAL-016", "Height of sku is mandatory.", HttpStatus.BAD_REQUEST),
    SKU_DEPTH_REQUIRED("SKU-VAL-017", "Depth of sku is mandatory.", HttpStatus.BAD_REQUEST),
    SKU_WEIGHT_MIN("SKU-VAL-018", "Weight must be greater than 0.", HttpStatus.BAD_REQUEST),
    SKU_WIDTH_MIN("SKU-VAL-019", "Width must be greater than 0.", HttpStatus.BAD_REQUEST),
    SKU_HEIGHT_MIN("SKU-VAL-020", "Height must be greater than 0.", HttpStatus.BAD_REQUEST),
    SKU_DEPTH_MIN("SKU-VAL-021", "Depth must be greater than 0.", HttpStatus.BAD_REQUEST),
    SKU_WEIGHT_FORMAT("SKU-VAL-022", "Price must contain up to 8 integer digits and 2 decimal places.", HttpStatus.BAD_REQUEST),
    SKU_WIDTH_FORMAT("SKU-VAL-023", "Price must contain up to 8 integer digits and 2 decimal places.", HttpStatus.BAD_REQUEST),
    SKU_HEIGHT_FORMAT("SKU-VAL-024", "Price must contain up to 8 integer digits and 2 decimal places.", HttpStatus.BAD_REQUEST),
    SKU_DEPTH_FORMAT("SKU-VAL-025", "Price must contain up to 8 integer digits and 2 decimal places.", HttpStatus.BAD_REQUEST),

    SKU_SPECIFICATION_REQUIRED("SKU-VAL-026", "List of specification is mandatory.", HttpStatus.BAD_REQUEST),
    SKU_SPECIFICATION_SIZE("SKU-VAL-027", "List of specification must be between 1 and 20.", HttpStatus.BAD_REQUEST),

    /* Option + Value domain exception */
    OPT_NAME_REQUIRED("OPT-VAL-001", "Name of option is mandatory.", HttpStatus.BAD_REQUEST),
    OPT_NAME_SIZE("OPT-VAL-002", "Name of option must be between 3 and 100 character.", HttpStatus.BAD_REQUEST),
    OPT_VALUE_REQUIRED("OPT-VAL-003", "Value of option is mandatory.", HttpStatus.BAD_REQUEST),
    OPT_VALUE_SIZE("OPT-VAL-004", "Value of option must be between 3 and 50 character.", HttpStatus.BAD_REQUEST),

    OPT_ALREADY_EXISTS("OPT-BUS-001", "Shop option is already exists.", HttpStatus.BAD_REQUEST),
    OPT_NOT_FOUND("OPT-BUS-002", "Shop option not found.", HttpStatus.BAD_REQUEST),

    /* Sku Attribute domain exception */
    SPE_NAME_REQUIRED("SPE-VAL-001", "Name of specification attribute is mandatory.", HttpStatus.BAD_REQUEST),
    SPE_NAME_SIZE("SPE-VAL-002", "Name of specification attribute must be between 3 and 255 character.", HttpStatus.BAD_REQUEST),
    SPE_VALUE_REQUIRED("SPE-VAL-003", "Value of specification attribute is mandatory.", HttpStatus.BAD_REQUEST),
    SPE_VALUE_SIZE("SPE-VAL-004", "Value of specification attribute must be between 3 and 255 character.", HttpStatus.BAD_REQUEST),

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
