package com.myproject.furnitureshop.service.imp;

import com.myproject.furnitureshop.config.RbacCacheConfigProperties;
import com.myproject.furnitureshop.service.RbacCacheService;
import com.myproject.furnitureshop.service.RedisMessagePublisher;
import com.myproject.furnitureshop.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RbacCacheServiceImp implements RbacCacheService {
    private static final String EMPTY_MARKER = "_EMPTY_";
    private static final String REDIS_TOPIC = "invalidate:role";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedisMessagePublisher redisMessagePublisher;

    private final RbacCacheConfigProperties rbacCacheConfigProperties;
    private final RedisTemplate<String, String> redisTemplate;
    private final RoleService roleService;

    public RbacCacheServiceImp(RbacCacheConfigProperties rbacCacheConfigProperties,
                               RedisTemplate<String, String> redisTemplate,
                               RoleService roleService,
                               RedisMessagePublisher redisMessagePublisher) {
        this.rbacCacheConfigProperties = rbacCacheConfigProperties;
        this.redisTemplate = redisTemplate;
        this.roleService = roleService;
        this.redisMessagePublisher = redisMessagePublisher;
    }

//    @Override
//    public Set<String> getPermissionsByRoleName(String roleName) {
//        String uppercasedRoleName = roleName.toUpperCase(Locale.ROOT);
//        String redisKey = this.rbacCacheConfigProperties.prefixKey() + ":" + uppercasedRoleName;
//        Set<String> redisCachedPermissions = this.redisTemplate.opsForSet().members(redisKey);
//
//        if(redisCachedPermissions != null && !redisCachedPermissions.isEmpty()) {
//            if(redisCachedPermissions.contains(EMPTY_MARKER)) {
//                return Collections.emptySet();
//            }
//            return redisCachedPermissions;
//        }
//
//        Set<String> permissionNameSet = this.roleService.findAllPermissionNamesByRoleName(roleName);
//
//        if(permissionNameSet == null || permissionNameSet.isEmpty()) {
//            this.redisTemplate.opsForSet().add(redisKey, EMPTY_MARKER);
//        } else {
//            String[] permissionsArr = permissionNameSet.toArray(new String[0]);
//            this.redisTemplate.opsForSet().add(redisKey, permissionsArr);
//        }
//
//        this.redisTemplate.expire(redisKey, Duration.ofDays(this.rbacCacheConfigProperties.rolesPermissionsTtlDays()));
//
//        return permissionNameSet;
//    }

    private String buildKey(String role) {
        String prefixKey = this.rbacCacheConfigProperties.prefixKey();
        return prefixKey + ":" + role.toUpperCase(Locale.ROOT);
    }

//    @Override
//    public Set<String> getPermissionsByRoleNames(Set<String> roleNames) {
//        if(roleNames == null || roleNames.isEmpty()) {
//            return Collections.emptySet();
//        }
//
//        Set<String> redisKeySet = new HashSet<>();
//        Set<String> unCachedRoleSet = new HashSet<>();
//
//        for(String roleName : roleNames) {
//            String redisKey = this.buildKey(roleName);
//
//            if(this.redisTemplate.hasKey(redisKey)) {
//                redisKeySet.add(redisKey);
//            } else {
//                unCachedRoleSet.add(roleName);
//            }
//        }
//
//        for(String role : unCachedRoleSet) {
//            String key = this.buildKey(role);
//
//            Set<String> perms = this.roleService.findAllPermissionNamesByRoleName(role);
//
//            if(perms == null || perms.isEmpty()) {
//                redisTemplate.opsForSet().add(key, EMPTY_MARKER);
//            } else {
//                String[] permissionsArr = perms.toArray(new String[0]);
//                this.redisTemplate.opsForSet().add(key, permissionsArr);
//            }
//
//            redisTemplate.expire(key, Duration.ofDays(
//                    rbacCacheConfigProperties.rolesPermissionsTtlDays()
//            ));
//
//            redisKeySet.add(key);
//        }
//
//        Set<String> result = new HashSet<>(Optional.ofNullable(
//                this.redisTemplate.opsForSet().union(redisKeySet)
//        ).orElse(Collections.emptySet()));
//
//        result.remove(EMPTY_MARKER);
//
//        return result;
//    }

    @Override
    public Set<String> getPermissionsByRoleNames(Set<String> roleNames) {
        Set<String> userPermissions = new HashSet<>();

        for(String roleName : roleNames) {
            String redisKey = this.buildKey(roleName);

            Set<String> cached = this.redisTemplate.opsForSet().members(redisKey);

            if(cached == null || cached.isEmpty()) {
                Set<String> perms = this.roleService.findAllPermissionNamesByRoleName(roleName);

                if(perms.isEmpty()) {
                    this.redisTemplate.opsForSet().add(redisKey, EMPTY_MARKER);
                } else {
                    this.redisTemplate.opsForSet().add(redisKey, perms.toArray(new String[0]));
                    userPermissions.addAll(perms);
                }

                redisTemplate.expire(redisKey,
                        Duration.ofDays(rbacCacheConfigProperties.rolesPermissionsTtlDays()));
            } else {
                if(!cached.contains(EMPTY_MARKER)) {
                    userPermissions.addAll(cached);
                }
            }
        }

        return userPermissions;
    }

    @Override
    public void invalidateCachedRole(Set<String> roleNameSet) {
        if(roleNameSet != null && !roleNameSet.isEmpty()) {
            String prefixKey = this.rbacCacheConfigProperties.prefixKey();

            Set<String> redisKeySet = roleNameSet.stream()
                    .filter(role -> role != null && !role.isBlank())
                    .map(role -> prefixKey + ":" + role.toUpperCase(Locale.ROOT))
                    .collect(Collectors.toSet());

            if(!redisKeySet.isEmpty()) {
                long deleted = this.redisTemplate.delete(redisKeySet);
                log.info("Invalidate role cache | keys={} | deleted={}", redisKeySet, deleted);
            }
        }
    }

    @Override
    public void pubForInvalidatingCachedRoles(Set<String> roleNames) {
        if(roleNames == null || roleNames.isEmpty()) return;

        String roleNamesJson = this.objectMapper.writeValueAsString(roleNames);

        this.redisMessagePublisher.publishMessage(REDIS_TOPIC, roleNamesJson);
    }
}
