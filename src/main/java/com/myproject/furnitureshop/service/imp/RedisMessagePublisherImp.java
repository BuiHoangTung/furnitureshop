package com.myproject.furnitureshop.service.imp;

import com.myproject.furnitureshop.service.RedisMessagePublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagePublisherImp implements RedisMessagePublisher {
    private final RedisTemplate<String, String> redisTemplate;

    public RedisMessagePublisherImp(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void publishMessage(String topic, String message) { // -> In this case just store ROLE_NAME for invalidate cached role in Redis.
        this.redisTemplate.convertAndSend(topic, message);
    }
}
