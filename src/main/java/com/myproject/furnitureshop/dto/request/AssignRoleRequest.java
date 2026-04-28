package com.myproject.furnitureshop.dto.request;

import java.util.Set;

public record AssignRoleRequest(
        Set<String> roles
) {
}
