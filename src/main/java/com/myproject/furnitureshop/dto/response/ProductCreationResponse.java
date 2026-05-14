package com.myproject.furnitureshop.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreationResponse {
    private long id;
    List<SkuCreationResponse> skus;
}
