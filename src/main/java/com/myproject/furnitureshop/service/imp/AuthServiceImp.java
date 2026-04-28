package com.myproject.furnitureshop.service.imp;

import com.myproject.furnitureshop.dto.request.AuthRequest;
import com.myproject.furnitureshop.dto.request.CreatePasswordRequest;
import com.myproject.furnitureshop.dto.request.JwtRefreshTokenRequest;
import com.myproject.furnitureshop.dto.request.LogoutRequest;
import com.myproject.furnitureshop.dto.response.AuthResponse;
import com.myproject.furnitureshop.dto.response.JwtResponse;
import com.myproject.furnitureshop.entity.RoleEntity;
import com.myproject.furnitureshop.entity.UserEntity;
import com.myproject.furnitureshop.enums.OtpType;
import com.myproject.furnitureshop.exception.AppException;
import com.myproject.furnitureshop.exception.ErrorCode;
import com.myproject.furnitureshop.model.JwtToken;
import com.myproject.furnitureshop.otp.model.OtpSuccessVerification;
import com.myproject.furnitureshop.service.*;
import com.myproject.furnitureshop.otp.service.OtpService;
import com.myproject.furnitureshop.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthServiceImp implements AuthService {
    private static final String OTP_SUCCESS_VERIFICATION = "otp:verified:";

    private final UserService userService;
    private final OtpService otpService;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final JwtHelper jwtHelper;
    private final TokenCacheService tokenCacheService;

    public AuthServiceImp(UserService userService,
                          OtpService otpService,
                          RedisTemplate<String, String> redisTemplate,
                          PasswordEncoder passwordEncoder,
                          RoleService roleService,
                          JwtHelper jwtHelper,
                          TokenCacheService tokenCacheService) {
        this.userService = userService;
        this.otpService = otpService;
        this.redisTemplate = redisTemplate;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.jwtHelper = jwtHelper;
        this.tokenCacheService = tokenCacheService;
    }

    private String generateRandomNameofUser() {
        return RandomStringUtils.secure().nextAlphabetic(10);
    }

    @Override
    public String signupAccount(String identifier) {
        boolean isUserEmailExist = this.userService.isUserExistedByEmail(identifier);

        if(isUserEmailExist) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        this.otpService.createAndStoreOtp(identifier, OtpType.SIGNUP);

        return identifier;
    }

    @Override
    public String createPassword(CreatePasswordRequest request) {
        String redisKey = OTP_SUCCESS_VERIFICATION + request.uuid();
        String redisValue = this.redisTemplate.opsForValue().get(redisKey);

        if(redisValue == null) {
            throw new AppException(ErrorCode.OTP_VERIFICATION_INVALID);
        }

        OtpSuccessVerification successVerification = this.objectMapper.readValue(redisValue, OtpSuccessVerification.class);
        String identifier = successVerification.getIdentifier();
        String password = this.passwordEncoder.encode(request.password());

        UserEntity userEntity = new UserEntity();
        RoleEntity roleEntity = this.roleService.findRoleByRoleName("ROLE_USER");

        userEntity.setEmail(identifier);
        userEntity.setPassword(password);
        userEntity.setRoles(Set.of(roleEntity));
        userEntity.setFullName(this.generateRandomNameofUser());
        this.userService.saveUserEntityToDB(userEntity);

        this.redisTemplate.delete(redisKey);

        return identifier;
    }

    @Override
    public AuthResponse checkLogin(AuthRequest authRequest) {
        UserEntity userEntity = this.userService.findUserByEmail(authRequest.email());

        if(!this.passwordEncoder.matches(authRequest.password(), userEntity.getPassword())) {
            throw new AppException(ErrorCode.AUTHENTICATION_FAILED);
        }

        Set<String> roles = userEntity.getRoles()
                .stream().map(RoleEntity::getName).collect(Collectors.toSet());

        return AuthResponse.builder()
                .userId(userEntity.getId())
                .roles(roles)
                .build();
    }

    @Override
    public JwtResponse generateJwtResponse(AuthResponse authResponse) {
        JwtToken accessToken = this.jwtHelper.generateToken(this.objectMapper.writeValueAsString(authResponse), false);
        JwtToken refreshToken = this.jwtHelper.generateToken(String.valueOf(authResponse.getUserId()), true);

        this.tokenCacheService.cacheWhiteListRefreshToken(refreshToken.getJti(), refreshToken.getExp());

        return JwtResponse.builder()
                .accessToken(accessToken.getToken())
                .refreshToken(refreshToken.getToken())
                .build();
    }


    @Override
    public boolean logout(LogoutRequest logoutRequest) {
        String refreshToken = logoutRequest.refreshToken();
        Jws<Claims> claimsJws;

        try {
            claimsJws = this.jwtHelper.getJws(refreshToken);

            Boolean isRefreshToken = claimsJws.getPayload().get("is-refresh-token", Boolean.class);

            if(!Boolean.TRUE.equals(isRefreshToken)) {
                throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
            }

            String jti = claimsJws.getPayload().getId();

            this.tokenCacheService.consumeRefreshToken(jti);

            return true;
        } catch(ExpiredJwtException e) {
            String jti = e.getClaims().getId();

            this.tokenCacheService.consumeRefreshToken(jti);

            return true;
        } catch(JwtException e) {
            throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    @Override
    public JwtResponse refreshToken(JwtRefreshTokenRequest refreshTokenRequest) {
        String token = refreshTokenRequest.refreshToken();
        Jws<Claims> claimsJws = this.jwtHelper.getJws(token);
        String sub = claimsJws.getPayload().getSubject();
        String jti = claimsJws.getPayload().getId();

        if(sub == null || sub.isBlank()) {
            throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        Boolean isRefreshToken = claimsJws.getPayload().get("is-refresh-token", Boolean.class);
        if(!Boolean.TRUE.equals(isRefreshToken)) {
            throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        boolean consumed = this.tokenCacheService.consumeRefreshToken(jti);
        if(!consumed) {
            log.warn("Refresh token reuse detected jti={}", jti);
            throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        long userId;

        try {
            userId = Long.parseLong(sub);
        } catch (NumberFormatException  e) {
            log.error("Invalid subject in refresh token traceId={}", jti);
            throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        UserEntity userEntity = this.userService.findUserById(userId);

        AuthResponse response = AuthResponse.builder()
                .userId(userEntity.getId())
                .roles(userEntity.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet()))
                .build();

        JwtToken newAccessToken = this.jwtHelper.generateToken(this.objectMapper.writeValueAsString(response), false);
        JwtToken newRefreshToken = this.jwtHelper.generateToken(String.valueOf(userEntity.getId()), true);

        this.tokenCacheService.cacheWhiteListRefreshToken(newRefreshToken.getJti(), newRefreshToken.getExp());

        return JwtResponse.builder()
                .accessToken(newAccessToken.getToken())
                .refreshToken(newRefreshToken.getToken())
                .build();
    }
}
