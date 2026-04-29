package com.myproject.furnitureshop.controller;

import com.myproject.furnitureshop.dto.request.AssignPermissionRequest;
import com.myproject.furnitureshop.dto.request.RoleCreationRequest;
import com.myproject.furnitureshop.dto.response.RoleResponse;
import com.myproject.furnitureshop.dto.response.SuccessResponse;
import com.myproject.furnitureshop.ratelimit.RateLimit;
import com.myproject.furnitureshop.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RateLimit(requests = 50)
    @PostMapping
    public ResponseEntity<SuccessResponse<RoleResponse>> createRole(@Valid @RequestBody RoleCreationRequest roleCreationRequest) {
        RoleResponse roleResponse = this.roleService.createRole(roleCreationRequest);

        SuccessResponse<RoleResponse> successResponse = SuccessResponse.of("Create role successfully.", roleResponse);

        return ResponseEntity.ok().body(successResponse);
    }

    @RateLimit
    @GetMapping
    public ResponseEntity<SuccessResponse<List<RoleResponse>>> getAllRoles() {
        List<RoleResponse> roleResponseList = this.roleService.getAllRoles();

        SuccessResponse<List<RoleResponse>> successResponse = SuccessResponse.of("", roleResponseList);

        return ResponseEntity.ok().body(successResponse);
    }

    @RateLimit(requests = 5)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable long id) {
        this.roleService.deleteRoleByRoleId(id);

        SuccessResponse<String> response = SuccessResponse.of("Delete role successfully", null);

        return ResponseEntity.ok().body(response);
    }

    @RateLimit(requests = 50)
    @PostMapping("/assign-permission/{id}")
    public ResponseEntity<?> toggleAssignPermissions(@PathVariable long id, @RequestBody AssignPermissionRequest request) {
        RoleResponse roleResponse = this.roleService.ToggleAssignPermissionsToRole(id, request.permissions());

        SuccessResponse<RoleResponse> successResponse = SuccessResponse.of("Assign successfully", roleResponse);

        return ResponseEntity.ok().body(successResponse);
    }
}
