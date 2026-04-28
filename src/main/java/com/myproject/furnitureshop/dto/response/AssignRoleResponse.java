package com.myproject.furnitureshop.dto.response;


import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignRoleResponse {
    private long id;
    private String email;
    private String fullName;
    private Set<RoleResponse> roles;
}
