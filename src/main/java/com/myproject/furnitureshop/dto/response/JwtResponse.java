package com.myproject.furnitureshop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myproject.furnitureshop.model.JwtToken;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
}
