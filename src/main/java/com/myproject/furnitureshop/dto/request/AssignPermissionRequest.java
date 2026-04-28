package com.myproject.furnitureshop.dto.request;

import java.util.Set;

public record AssignPermissionRequest(
        Set<String> permissions
) {
}
