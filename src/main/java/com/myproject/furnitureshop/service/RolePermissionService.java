package com.myproject.furnitureshop.service;

import com.myproject.furnitureshop.entity.PermissionEntity;

public interface RolePermissionService {
    String assignNewPermissionToAdminRole(PermissionEntity permissionEntity);
}
