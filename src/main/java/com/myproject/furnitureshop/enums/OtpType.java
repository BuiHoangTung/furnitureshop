package com.myproject.furnitureshop.enums;

public enum OtpType {
    SIGNUP(10, 60)
    ;
    OtpType(int expiryTimeMinutes, int ttlMinutes) {
        this.expiryTimeMinutes = expiryTimeMinutes;
        this.ttlMinutes = ttlMinutes;
    }

    private final int expiryTimeMinutes;
    private final int ttlMinutes;

    public int getExpiryTimeMinutes() {
        return expiryTimeMinutes;
    }

    public int getTtlMinutes() {
        return ttlMinutes;
    }
}
