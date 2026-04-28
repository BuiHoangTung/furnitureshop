package com.myproject.furnitureshop.security;

import com.myproject.furnitureshop.dto.request.AuthRequest;
import com.myproject.furnitureshop.dto.response.AuthResponse;
import com.myproject.furnitureshop.service.AuthService;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final AuthService authService;

    public CustomAuthenticationProvider(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        AuthRequest authRequest = new AuthRequest(username, password);

        AuthResponse authResponse = this.authService.checkLogin(authRequest);
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

        authResponse.getRoles().forEach(role -> {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
        });

        return new UsernamePasswordAuthenticationToken(authResponse, "", simpleGrantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
