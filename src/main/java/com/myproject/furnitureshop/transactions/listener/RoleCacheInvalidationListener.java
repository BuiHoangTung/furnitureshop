package com.myproject.furnitureshop.transactions.listener;

import com.myproject.furnitureshop.service.RbacCacheService;
import com.myproject.furnitureshop.transactions.event.RoleInvalidationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class RoleCacheInvalidationListener {
    private final RbacCacheService rbacCacheService;

    public RoleCacheInvalidationListener(RbacCacheService rbacCacheService) {
        this.rbacCacheService = rbacCacheService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void invalidatedCachedRoles(RoleInvalidationEvent event) {
        log.info("Invalidating roles from Redis: {}", event.roles());

        this.rbacCacheService.pubForInvalidatingCachedRoles(event.roles());
    }
}
