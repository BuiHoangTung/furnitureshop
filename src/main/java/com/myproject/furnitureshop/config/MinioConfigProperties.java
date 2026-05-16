package com.myproject.furnitureshop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minio")
public record MinioConfigProperties(
        String endpoint,
        String rootName,
        String rootPassword,
        String bucketName
) {}
