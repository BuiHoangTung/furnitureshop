package com.myproject.furnitureshop.service;

import com.myproject.furnitureshop.dto.request.PermissionCreationRequest;
import com.myproject.furnitureshop.dto.response.PermissionResponse;
import com.myproject.furnitureshop.entity.PermissionEntity;

import java.util.List;

public interface PermissionService {
    PermissionResponse createPermission(PermissionCreationRequest permissionCreationRequest);
    List<PermissionResponse> getAllPermissions();
    PermissionEntity getPermissionByName(String permissionName);
    void deletePermissionByPermissionId(long id);
}
