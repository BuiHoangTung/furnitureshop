package com.myproject.furnitureshop.controller;

import com.myproject.furnitureshop.dto.request.AssignRoleRequest;
import com.myproject.furnitureshop.dto.response.AssignRoleResponse;
import com.myproject.furnitureshop.dto.response.SuccessResponse;
import com.myproject.furnitureshop.dto.response.UserProfileResponse;
import com.myproject.furnitureshop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/assign-role/{id}")
    public ResponseEntity<SuccessResponse<AssignRoleResponse>> toggleAssignRoleToUser(@PathVariable long id, @RequestBody AssignRoleRequest request) {
        AssignRoleResponse assignRoleResponse = this.userService.toggleAssignRole(id, request);

        SuccessResponse<AssignRoleResponse> successResponse = SuccessResponse.of("Assign role to user successfully.", assignRoleResponse);

        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<SuccessResponse<UserProfileResponse>> getUserProfile() {
        UserProfileResponse userProfileResponse = this.userService.getUserProfile();

        SuccessResponse<UserProfileResponse> response = SuccessResponse.of("", userProfileResponse);

        return ResponseEntity.ok().body(response);
    }
}
