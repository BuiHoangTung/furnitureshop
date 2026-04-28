package com.myproject.furnitureshop.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionResponse {
    private long id;
    private String name;
    private String description;
}
