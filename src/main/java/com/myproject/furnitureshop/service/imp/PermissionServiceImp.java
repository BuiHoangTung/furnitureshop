package com.myproject.furnitureshop.service.imp;

import com.myproject.furnitureshop.dto.request.PermissionCreationRequest;
import com.myproject.furnitureshop.dto.response.PermissionResponse;
import com.myproject.furnitureshop.entity.PermissionEntity;
import com.myproject.furnitureshop.entity.RoleEntity;
import com.myproject.furnitureshop.exception.AppException;
import com.myproject.furnitureshop.exception.ErrorCode;
import com.myproject.furnitureshop.mapper.PermissionMapper;
import com.myproject.furnitureshop.repository.PermissionRepository;
import com.myproject.furnitureshop.service.PermissionService;
import com.myproject.furnitureshop.service.RolePermissionService;
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

@Service
@Slf4j
public class PermissionServiceImp implements PermissionService {
    private final PermissionMapper permissionMapper;
    private final PermissionRepository permissionRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RolePermissionService rolePermissionService;

    public PermissionServiceImp(PermissionMapper permissionMapper,
                                PermissionRepository permissionRepository,
                                ApplicationEventPublisher applicationEventPublisher,
                                RolePermissionService rolePermissionService) {
        this.permissionMapper = permissionMapper;
        this.permissionRepository = permissionRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.rolePermissionService = rolePermissionService;
    }

    @PreAuthorize("hasAuthority('PERMISSION_CREATE')")
    @Transactional
    @Override
    public PermissionResponse createPermission(PermissionCreationRequest permissionCreationRequest) {
        boolean isPermissionExist = this.permissionRepository.existsPermissionEntitiesByName(permissionCreationRequest.name());

        if(isPermissionExist) {
            throw new AppException(ErrorCode.PERMISSION_ALREADY_EXISTS);
        }

        PermissionEntity permissionEntity = this.permissionMapper.toPermissionEntity(permissionCreationRequest);

        permissionEntity = this.permissionRepository.save(permissionEntity);

        String roleName = this.rolePermissionService.assignNewPermissionToAdminRole(permissionEntity);

        this.applicationEventPublisher.publishEvent(new RoleInvalidationEvent(Set.of(roleName)));

        return this.permissionMapper.toPermissionResponse(permissionEntity);
    }

    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    @Override
    public List<PermissionResponse> getAllPermissions() {
        return this.permissionRepository.findAll()
                .stream()
                .map(this.permissionMapper::toPermissionResponse).toList();
    }

    @Override
    public PermissionEntity getPermissionByName(String permissionName) {
        return this.permissionRepository.findPermissionEntityByName(permissionName)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
    }

    @PreAuthorize("hasAuthority('PERMISSION_DELETE')")
    @Override
    @Transactional
    public void deletePermissionByPermissionId(long id) {
        PermissionEntity permissionEntity = this.permissionRepository
                .findPermissionEntitiesById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        Set<String> roleNames = permissionEntity.getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet());

        new HashSet<>(permissionEntity.getRoles()).forEach(role -> {
            role.removePermission(permissionEntity);
        });

        this.permissionRepository.delete(permissionEntity);

        this.applicationEventPublisher.publishEvent(new RoleInvalidationEvent(roleNames));
    }
}
