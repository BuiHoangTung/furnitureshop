package com.myproject.furnitureshop.security;

import com.myproject.furnitureshop.dto.response.AuthResponse;
import com.myproject.furnitureshop.service.RbacCacheService;
import com.myproject.furnitureshop.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CustomFilter extends OncePerRequestFilter {
    private final JwtHelper jwtHelper;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final RbacCacheService rbacCacheService;

    public CustomFilter(JwtHelper jwtHelper,
                        @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver,
                        RbacCacheService rbacCacheService) {
        this.jwtHelper = jwtHelper;
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.rbacCacheService = rbacCacheService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ") && authorizationHeader.length() > 7) {
            String token = authorizationHeader.substring(7);
            try {
                Jws<Claims> claimsJws = this.jwtHelper.getJws(token);
                String data = claimsJws.getPayload().getSubject();

                if(data != null) {
                    AuthResponse authResponse = this.objectMapper.readValue(data, AuthResponse.class);

                    List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();

                    this.rbacCacheService.getPermissionsByRoleNames(authResponse.getRoles()).forEach(perm -> {
                        grantedAuthorityList.add(new SimpleGrantedAuthority(perm));
                    });

                    authResponse.getRoles().forEach(role -> {
                        grantedAuthorityList.add(new SimpleGrantedAuthority(role));
                    });

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(authResponse, "", grantedAuthorityList);

                    log.info("User login | user={} | authorities={}", authResponse.getUserId(), grantedAuthorityList);

                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    securityContext.setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch(JwtException e) {
                log.error("JWT validation failed", e);
                this.handlerExceptionResolver.resolveException(request, response, null, e);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
