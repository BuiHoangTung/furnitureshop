package com.myproject.furnitureshop.service.imp;

import com.myproject.furnitureshop.dto.request.RoleCreationRequest;
import com.myproject.furnitureshop.dto.response.RoleResponse;
import com.myproject.furnitureshop.entity.PermissionEntity;
import com.myproject.furnitureshop.entity.RoleEntity;
import com.myproject.furnitureshop.exception.AppException;
import com.myproject.furnitureshop.exception.ErrorCode;
import com.myproject.furnitureshop.mapper.RoleMapper;
import com.myproject.furnitureshop.repository.RoleRepository;
import com.myproject.furnitureshop.service.PermissionService;
import com.myproject.furnitureshop.service.RoleService;
import com.myproject.furnitureshop.transactions.event.RoleInvalidationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionService permissionService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public RoleServiceImp(RoleRepository roleRepository,
                          RoleMapper roleMapper,
                          PermissionService permissionService,
                          ApplicationEventPublisher applicationEventPublisher) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.permissionService = permissionService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public RoleEntity findRoleByRoleName(String roleName) {
        return this.roleRepository.findRoleEntityByName(roleName)
                .orElseThrow(() ->  new AppException(ErrorCode.ROLE_NOT_FOUND));
    }

    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    @Override
    public RoleResponse createRole(RoleCreationRequest roleCreationRequest) {
        boolean isRoleExist = this.roleRepository.existsRoleEntitiesByName(roleCreationRequest.name());

        if(isRoleExist) {
            throw new AppException(ErrorCode.ROLE_ALREADY_EXISTS);
        }

        RoleEntity roleEntity = this.roleMapper.toRoleEntity(roleCreationRequest);

        Set<PermissionEntity> permissionEntitySet = roleCreationRequest.permissionList().stream()
                .map(permissionService::getPermissionByName)
                .collect(Collectors.toSet());

        roleEntity.setPermissions(permissionEntitySet);

        roleEntity = this.roleRepository.save(roleEntity);

        return this.roleMapper.toRoleResponse(roleEntity);
    }

    @PreAuthorize("hasAuthority('ROLE_READ')")
    @Override
    public List<RoleResponse> getAllRoles() {
        return this.roleRepository.findAll().stream()
                .map(this.roleMapper::toRoleResponse)
                .toList();
    }

    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    @Override
    @Transactional
    public void deleteRoleByRoleId(long id) {
        RoleEntity roleEntity = this.roleRepository.findRoleEntityById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        new HashSet<>(roleEntity.getUsers()).forEach(userEntity -> {
            userEntity.removeRole(roleEntity);
        });

        this.roleRepository.delete(roleEntity);

        this.applicationEventPublisher.publishEvent(new RoleInvalidationEvent(Set.of(roleEntity.getName())));
    }

    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    @Override
    @Transactional
    public RoleResponse ToggleAssignPermissionsToRole(long id, Set<String> permissions) { // -> Find way to improve.
        RoleEntity roleEntity = this.roleRepository.findRoleEntityById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        Set<PermissionEntity> newPermissionEntitySet = permissions.stream()
                .map(this.permissionService::getPermissionByName)
                .collect(Collectors.toSet());

//        Set<PermissionEntity> currentPermissionEntitySet = roleEntity.getPermissions(); -> Cause java.util.ConcurrentModificationException
        Set<PermissionEntity> currentPermissionEntitySet = new HashSet<>(roleEntity.getPermissions());
        for(PermissionEntity p : currentPermissionEntitySet) {
            if(!newPermissionEntitySet.contains(p)) {
                roleEntity.removePermission(p);
            }
        }

        for(PermissionEntity p : newPermissionEntitySet) {
            if(!roleEntity.getPermissions().contains(p)) {
                roleEntity.addPermission(p);
            }
        }

        this.roleRepository.save(roleEntity);

        this.applicationEventPublisher.publishEvent(new RoleInvalidationEvent(Set.of(roleEntity.getName())));

        return this.roleMapper.toRoleResponse(roleEntity);
    }

    @Override
    public Set<String> findAllPermissionNamesByRoleName(String roleName) {
        return this.roleRepository.findPermissionNamesByRoleName(roleName);
    }
}
