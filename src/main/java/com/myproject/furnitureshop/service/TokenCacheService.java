package com.myproject.furnitureshop.service;

import java.util.Date;

public interface TokenCacheService {
    void cacheWhiteListRefreshToken(String jit, Date exp);
//    boolean isValidRefreshToken(String jti);
//    void invalidatedRefreshToken(String jti);
    boolean consumeRefreshToken(String jti);
}