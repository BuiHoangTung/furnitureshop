package com.myproject.furnitureshop.service;

public interface RedisMessagePublisher {
    void publishMessage(String topic, String message);
}
