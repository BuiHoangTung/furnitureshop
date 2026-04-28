package com.myproject.furnitureshop.notification.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "notification-service.mq")
public record NotificationMQConfigProperties (
        String exchange,
        String emailQueue,
        String inappQueue,
        String emailRoutingKey,
        String inappRoutingKey
){}
