package com.myproject.furnitureshop.dto.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopOptionDetailResponse {
    private long id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean isActive;
}
