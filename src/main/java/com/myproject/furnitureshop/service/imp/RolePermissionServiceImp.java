package com.myproject.furnitureshop.service.imp;

import com.myproject.furnitureshop.entity.PermissionEntity;
import com.myproject.furnitureshop.entity.RoleEntity;
import com.myproject.furnitureshop.exception.AppException;
import com.myproject.furnitureshop.exception.ErrorCode;
import com.myproject.furnitureshop.repository.RoleRepository;
import com.myproject.furnitureshop.service.RolePermissionService;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImp implements RolePermissionService {
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private final RoleRepository roleRepository;

    public RolePermissionServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public String assignNewPermissionToAdminRole(PermissionEntity permissionEntity) {
        RoleEntity roleEntity = this.roleRepository.findRoleEntityByName(ROLE_ADMIN)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        roleEntity.addPermission(permissionEntity);

        return roleEntity.getName();
    }
}
