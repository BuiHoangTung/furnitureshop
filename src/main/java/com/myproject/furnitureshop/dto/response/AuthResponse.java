package com.myproject.furnitureshop.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private long userId;
    private Set<String> roles;
}
