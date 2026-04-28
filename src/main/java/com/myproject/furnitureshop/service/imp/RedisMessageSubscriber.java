package com.myproject.furnitureshop.service.imp;

import com.myproject.furnitureshop.service.RbacCacheService;
import org.jspecify.annotations.Nullable;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.HashSet;

@Service
public class RedisMessageSubscriber implements MessageListener {
    private final RbacCacheService rbacCacheService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RedisMessageSubscriber(RbacCacheService rbacCacheService) {
        this.rbacCacheService = rbacCacheService;
    }

    @Override
    public void onMessage(Message message, byte @Nullable [] pattern) {
        HashSet<String> roles = this.objectMapper.readValue(new String(message.getBody()), new TypeReference<>(){});

        this.rbacCacheService.invalidateCachedRole(roles);
    }
}
