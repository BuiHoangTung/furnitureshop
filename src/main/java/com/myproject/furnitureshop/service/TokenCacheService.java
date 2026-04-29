package com.myproject.furnitureshop.service;

import java.util.Date;

public interface TokenCacheService {
    void cacheWhiteListRefreshToken(String jti, Date exp, String token);
    boolean consumeRefreshToken(String jti);
}