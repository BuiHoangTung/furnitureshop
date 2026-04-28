package com.myproject.furnitureshop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rbac-cache")
public record RbacCacheConfigProperties(
    String prefixKey,
    long rolesPermissionsTtlDays
) {
}
