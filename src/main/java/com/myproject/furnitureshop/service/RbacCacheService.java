package com.myproject.furnitureshop.service;

import java.util.Set;

public interface RbacCacheService {
    Set<String> getPermissionsByRoleNames(Set<String> roleNames);
    void invalidateCachedRole(Set<String> roleNameSet);
    void pubForInvalidatingCachedRoles(Set<String> roleNames);
}
