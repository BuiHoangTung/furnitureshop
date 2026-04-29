package com.myproject.furnitureshop.controller;

import com.myproject.furnitureshop.dto.request.PermissionCreationRequest;
import com.myproject.furnitureshop.dto.response.PermissionResponse;
import com.myproject.furnitureshop.dto.response.SuccessResponse;
import com.myproject.furnitureshop.ratelimit.RateLimit;
import com.myproject.furnitureshop.service.PermissionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }


    @RateLimit(requests = 50)
    @PostMapping
    public ResponseEntity<SuccessResponse<PermissionResponse>> createPermission(@Valid @RequestBody PermissionCreationRequest request) {
        PermissionResponse permissionResponse = this.permissionService.createPermission(request);

        SuccessResponse<PermissionResponse> response = SuccessResponse.of("Create permission successfully.", permissionResponse);

        return ResponseEntity.ok().body(response);
    }

    @RateLimit
    @GetMapping
    public ResponseEntity<?> getAllPermissions() {
        List<PermissionResponse> permissionResponseList = this.permissionService.getAllPermissions();
        SuccessResponse<List<PermissionResponse>> response = SuccessResponse.of("", permissionResponseList);

        return ResponseEntity.ok().body(response);
    }

    @RateLimit(requests = 5)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePermission(@PathVariable long id) {
        this.permissionService.deletePermissionByPermissionId(id);

        SuccessResponse<String> successResponse = SuccessResponse.of("Delete permission successfully.", null);

        return ResponseEntity.ok().body(successResponse);
    }
}
