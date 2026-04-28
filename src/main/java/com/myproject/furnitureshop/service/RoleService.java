package com.myproject.furnitureshop.service;

import com.myproject.furnitureshop.dto.request.RoleCreationRequest;
import com.myproject.furnitureshop.dto.response.RoleResponse;
import com.myproject.furnitureshop.entity.RoleEntity;

import java.util.List;
import java.util.Set;

public interface RoleService {
    RoleEntity findRoleByRoleName(String roleName);
    RoleResponse createRole(RoleCreationRequest roleCreationRequest);
    List<RoleResponse> getAllRoles();
    void deleteRoleByRoleId(long id);
    RoleResponse ToggleAssignPermissionsToRole(long id, Set<String> permissions);
    Set<String> findAllPermissionNamesByRoleName(String roleName);
}
