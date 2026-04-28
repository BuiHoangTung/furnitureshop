package com.myproject.furnitureshop.model;

import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtToken {
    private String jti;
    private String token;
    private Date exp;

    public long getExpSeconds() {
        long exp = Duration.between(
                Instant.now(),
                this.exp.toInstant()
        ).toSeconds();

        return Math.max(exp, 0);
    }
}
