package com.myproject.furnitureshop.controller;

import com.myproject.furnitureshop.dto.request.*;
import com.myproject.furnitureshop.dto.response.AuthResponse;
import com.myproject.furnitureshop.dto.response.JwtResponse;
import com.myproject.furnitureshop.dto.response.SuccessResponse;
import com.myproject.furnitureshop.dto.response.TurnstileResponse;
import com.myproject.furnitureshop.exception.AppException;
import com.myproject.furnitureshop.exception.ErrorCode;
import com.myproject.furnitureshop.service.AuthService;
import com.myproject.furnitureshop.service.TurnstileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final TurnstileService turnstileService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(TurnstileService turnstileService,
                          AuthService authService,
                          AuthenticationManager authenticationManager) {
        this.turnstileService = turnstileService;
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup/email")
    public ResponseEntity<SuccessResponse<String>> signUpWithUsername(HttpServletRequest request,
                                                @Valid @RequestBody SendOtpRequest sendOtpRequest){
        String remoteip = request.getHeader("CF-Connecting-IP");
        if(remoteip == null) {
            remoteip = request.getHeader("X-Forwarded-For");
        }
        if(remoteip == null) {
            remoteip = request.getRemoteAddr();
        }

        TurnstileResponse turnstileResponse = turnstileService.validateToken(sendOtpRequest.turnStileToken(), remoteip);
        if(turnstileResponse.isSuccess()){
            String identifier = this.authService.signupAccount(sendOtpRequest.identifier().trim());

            SuccessResponse<String> response = SuccessResponse.of("", identifier);

            return ResponseEntity.ok(response);
        } else {
            throw new AppException(ErrorCode.TURNSTILE_INVALID_TOKEN);
        }
    }

    @PostMapping("/password")
    public ResponseEntity<SuccessResponse<String>> createPassword(@Valid @RequestBody CreatePasswordRequest request) {
        String identifier = this.authService.createPassword(request);

        SuccessResponse<String> response = SuccessResponse.of("Create account successfully.", identifier);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<JwtResponse>> authen(@Valid @RequestBody AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password());

        Authentication authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        AuthResponse authResponse = (AuthResponse)authentication.getPrincipal();

        JwtResponse jwtResponse = this.authService.generateJwtResponse(authResponse);

        SuccessResponse<JwtResponse> response = SuccessResponse
                .of("Login successfully.", jwtResponse);

        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody LogoutRequest request) {
        SuccessResponse<Boolean> response = SuccessResponse
                .of("Logout successfully.", this.authService.logout(request));

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<SuccessResponse<JwtResponse>> refreshAccessToken(@Valid @RequestBody JwtRefreshTokenRequest request) {
        JwtResponse jwtResponse = this.authService.refreshToken(request);

        SuccessResponse<JwtResponse> response = SuccessResponse.of("Refresh token successfully.", jwtResponse);

        return ResponseEntity.ok().body(response);
    }
}














