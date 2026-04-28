package com.myproject.furnitureshop.utils;

import com.myproject.furnitureshop.model.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtHelper {
    @Value("${secret-key}")
    private String strKey;
//    private static final long ACCESS_TOKEN_EXPIRY_TIME = 60 * 60 * 1000;

    @Value("${jwt.expiry-time.access-token-minutes}")
    private long ACCESS_TOKEN_EXPIRY_TIME;

    @Value("${jwt.expiry-time.refresh-token-days}")
    private long REFRESH_TOKEN_EXPIRY_TIME;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
    }

    public JwtToken generateToken(String data, boolean isRefreshToken) {
        SecretKey secretKey = this.getSecretKey();

        String jti = UUID.randomUUID().toString();

        Date expiryTime = isRefreshToken
                ? new Date(Instant.now().plus(REFRESH_TOKEN_EXPIRY_TIME, ChronoUnit.DAYS).toEpochMilli())
                : new Date(Instant.now().plus(ACCESS_TOKEN_EXPIRY_TIME, ChronoUnit.MINUTES).toEpochMilli());

        String token = Jwts.builder()
                .id(jti)
                .subject(data)
                .claim("is-refresh-token", isRefreshToken)
                .expiration(expiryTime)
                .signWith(secretKey)
                .compact();

        return JwtToken.builder()
                .jti(jti)
                .exp(expiryTime)
                .token(token)
                .build();
    }

    public String decodeToken(String token) {
        SecretKey secretKey = this.getSecretKey();
        return Jwts.parser()
                .verifyWith(secretKey)
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public Jws<Claims> getJws(String token) {
        SecretKey secretKey = this.getSecretKey();
        return Jwts.parser()
                .verifyWith(secretKey)
                .build().parseSignedClaims(token);
    }
}
