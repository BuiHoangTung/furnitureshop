package com.myproject.furnitureshop.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {
    private long id;
    private String name;
    private String description;
    private Set<PermissionResponse> permissions;
}
