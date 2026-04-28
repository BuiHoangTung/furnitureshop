package com.myproject.furnitureshop.service;

import com.myproject.furnitureshop.dto.request.AuthRequest;
import com.myproject.furnitureshop.dto.request.CreatePasswordRequest;
import com.myproject.furnitureshop.dto.request.JwtRefreshTokenRequest;
import com.myproject.furnitureshop.dto.request.LogoutRequest;
import com.myproject.furnitureshop.dto.response.AuthResponse;
import com.myproject.furnitureshop.dto.response.JwtResponse;

public interface AuthService {
    String signupAccount(String email);
    String createPassword(CreatePasswordRequest createPasswordRequest);
    AuthResponse checkLogin(AuthRequest authRequest);
    JwtResponse generateJwtResponse(AuthResponse authResponse);
    boolean logout(LogoutRequest logoutRequest);
    JwtResponse refreshToken(JwtRefreshTokenRequest request);
}
