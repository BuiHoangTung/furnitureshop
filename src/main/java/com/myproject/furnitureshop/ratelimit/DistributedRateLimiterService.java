package com.myproject.furnitureshop.ratelimit;

import com.myproject.furnitureshop.exception.AppException;
import com.myproject.furnitureshop.exception.ErrorCode;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.function.Supplier;

@Service
public class DistributedRateLimiterService {
    private static final String RATE_LIMIT_PREFIX = "rateLimit:";

    private final ProxyManager<String> proxyManager;

    public DistributedRateLimiterService(ProxyManager<String> proxyManager) {
        this.proxyManager = proxyManager;
    }

    private Supplier<BucketConfiguration> getConfigurationSupplier(int rpm, Duration duration) {
        return () -> BucketConfiguration.builder()
                .addLimit(
                        Bandwidth.builder()
                                .capacity(rpm)
                                .refillGreedy(rpm, duration)
                                .build()
                )
                .build();
    }

    private boolean tryConsume(String key, int rpm, Duration duration) {
        return this.proxyManager
                .builder()
                .build(RATE_LIMIT_PREFIX + key, getConfigurationSupplier(rpm, duration))
                .tryConsume(1);
    }

    public void handleRateLimit(String key, int rpm, Duration duration) {
        if(!this.tryConsume(key, rpm, duration)) {
            throw new AppException(ErrorCode.MANY_REQUEST);
        }
    }
}
