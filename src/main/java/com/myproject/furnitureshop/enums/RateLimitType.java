package com.myproject.furnitureshop.enums;

import lombok.Getter;

@Getter
public enum RateLimitType {
    AUTH("authservice"),
    REFRESH_TOKEN("refreshtoken")

    ;
    RateLimitType(String type) {
        this.type = type;
    }

    private final String type;
}
