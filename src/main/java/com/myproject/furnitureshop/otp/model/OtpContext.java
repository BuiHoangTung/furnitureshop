package com.myproject.furnitureshop.otp.model;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpContext {
    private String otp;
    private Instant expiryAt;
    private int verifyAttempts;
    private int resendAttempts;
    private Instant nextResendAt;
}
