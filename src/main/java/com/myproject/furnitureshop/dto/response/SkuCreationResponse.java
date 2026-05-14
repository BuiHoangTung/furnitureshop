package com.myproject.furnitureshop.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkuCreationResponse {
    private long id;
    private String code;
}
