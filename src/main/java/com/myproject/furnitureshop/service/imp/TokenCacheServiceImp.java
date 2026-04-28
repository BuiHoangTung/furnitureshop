package com.myproject.furnitureshop.service.imp;

import com.myproject.furnitureshop.service.TokenCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TokenCacheServiceImp implements TokenCacheService {
    private static final String DEFAULT_VALUE = "whitelisted";
    private static final String PREFIX_KEY = "jwt:jti:";

    private final RedisTemplate<String, String> redisTemplate;

    public TokenCacheServiceImp(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String builtRedisKey(String jti) {
        return PREFIX_KEY + jti;
    }

    private long setTtl (Date exp) {
        return Duration.between(
                Instant.now(),
                exp.toInstant()
        ).toMillis();
    }

    @Override
    public void cacheWhiteListRefreshToken(String jti, Date exp) {
        long ttl = this.setTtl(exp);

        if(ttl <= 0) return;

        this.redisTemplate.opsForValue().set(this.builtRedisKey(jti), DEFAULT_VALUE, ttl, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean consumeRefreshToken(String jti) {
        return Boolean.TRUE
                .equals(this.redisTemplate.delete(this.builtRedisKey(jti)));
    }

//    @Override
//    public boolean isValidRefreshToken(String jti) {
//        String redisKey = this.builtRedisKey(jti);
//
//        return this.redisTemplate.hasKey(redisKey);
//    }
//
//    @Override
//    public void invalidatedRefreshToken(String jti) {
//        String redisKey = this.builtRedisKey(jti);
//
//        Boolean deleted = this.redisTemplate.delete(redisKey);
//        log.info("Invalidate role cache | keys={} | deleted={}", redisKey, deleted);
//    }
}
