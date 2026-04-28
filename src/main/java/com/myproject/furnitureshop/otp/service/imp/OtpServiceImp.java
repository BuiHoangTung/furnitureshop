package com.myproject.furnitureshop.otp.service.imp;

import com.myproject.furnitureshop.dto.request.OtpVerificationRequest;
import com.myproject.furnitureshop.enums.NotificationChannels;
import com.myproject.furnitureshop.enums.OtpType;
import com.myproject.furnitureshop.exception.AppException;
import com.myproject.furnitureshop.exception.ErrorCode;
import com.myproject.furnitureshop.notification.model.NotificationMessage;
import com.myproject.furnitureshop.notification.producer.NotificationProducer;
import com.myproject.furnitureshop.otp.model.OtpContext;
import com.myproject.furnitureshop.otp.model.OtpSuccessVerification;
import com.myproject.furnitureshop.otp.service.OtpService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class OtpServiceImp implements OtpService {
    private static final int OTP_RESEND_COOL_DOWN_SECONDS = 45;
    private static final String OTP_VERIFICATION_KEY_PREFIX = "otp:";
    private static final int OTP_LENGTH = 6;
    private static final int OTP_VERIFY_ATTEMPTS = 5;
    private static final int OTP_RESEND_ATTEMPTS = 5;

    private static final String OTP_SUCCESS_VERIFICATION = "otp:verified:";

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PasswordEncoder passwordEncoder;
    private final NotificationProducer notificationProducer;

    public OtpServiceImp(RedisTemplate<String, String> redisTemplate,
                         PasswordEncoder passwordEncoder,
                         NotificationProducer notificationProducer) {
        this.redisTemplate = redisTemplate;
        this.passwordEncoder = passwordEncoder;
        this.notificationProducer = notificationProducer;
    }

    private String generateOtp() {
        SecureRandom secureRandom = new SecureRandom();

        return secureRandom
            .ints(OTP_LENGTH, 0, 9)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining());
    }

    private void sendOtp(Map<String, Object> templateValues, String identifier) {
        NotificationMessage notificationMessage = NotificationMessage.builder()
                .recipient(identifier)
                .subject("[FurnitureShop] Verify Your Email - OTP Code")
                .templateName("verify-otp-email")
                .channel(NotificationChannels.EMAIL)
                .templateVariables(templateValues)
                .build();

        this.notificationProducer.pubNotification(notificationMessage);
    }

    private String confirmValidIdentifier(String identifier) {
        String uuid = UUID.randomUUID().toString();
        String redisKey = OTP_SUCCESS_VERIFICATION + uuid;
        OtpSuccessVerification otpSuccessVerification = OtpSuccessVerification.builder()
                .identifier(identifier)
                .build();

        String otpSuccessVerificationJson = this.objectMapper.writeValueAsString(otpSuccessVerification);

        this.redisTemplate.opsForValue().set(redisKey, otpSuccessVerificationJson, Duration.ofMinutes(30));

        return uuid;
    }

    @Override
    public void createAndStoreOtp(String identifier, OtpType otpType) {
        String otp = this.generateOtp();
        String redisVerificationKey = OTP_VERIFICATION_KEY_PREFIX + identifier;

        OtpContext otpContext = OtpContext.builder()
                .otp(this.passwordEncoder.encode(otp))
                .expiryAt(Instant.now().plus(otpType.getExpiryTimeMinutes(), ChronoUnit.MINUTES))
                .verifyAttempts(OTP_VERIFY_ATTEMPTS)
                .resendAttempts(OTP_RESEND_ATTEMPTS)
                .nextResendAt(Instant.now().plus(OTP_RESEND_COOL_DOWN_SECONDS, ChronoUnit.SECONDS))
                .build();

        String otpContextJson = objectMapper.writeValueAsString(otpContext);

        this.redisTemplate.opsForValue().set(redisVerificationKey, otpContextJson, Duration.ofMinutes(otpType.getTtlMinutes()));

        try {
            this.sendOtp(Map.of("otp", otp, "expirationTime", otpType.getExpiryTimeMinutes()), identifier);
        } catch(Exception e) {
            this.redisTemplate.delete(redisVerificationKey);
            throw new AppException(ErrorCode.OTP_SEND_FAILED);
        }
    }

    @Override
    public void resendOtp(String identifier, OtpType otpType) {
        String otpVerificationKey = OTP_VERIFICATION_KEY_PREFIX + identifier;
        String redisValue = this.redisTemplate.opsForValue().get(otpVerificationKey);

        if(redisValue == null) {
            throw new AppException(ErrorCode.OTP_SESSION_EXPIRED);
        }

        OtpContext otpContext = this.objectMapper.readValue(redisValue, OtpContext.class);

        if(otpContext.getResendAttempts() <= 0) {
            throw new AppException(ErrorCode.OTP_RESEND_FAILED);
        }
        if(Instant.now().isBefore(otpContext.getNextResendAt())) {
            throw new AppException(ErrorCode.OTP_RESEND_NOT_ALLOWED);
        }

        Long ttl = this.redisTemplate.getExpire(otpVerificationKey); // -> Get ttl from redis with second unit
        int resendAttempt = otpContext.getResendAttempts() - 1;

        if (ttl == null || ttl <= 0) {
            throw new AppException(ErrorCode.OTP_SESSION_EXPIRED);
        }

        String newOtp = this.generateOtp();
        OtpContext newOtpContext = OtpContext.builder()
                .otp(this.passwordEncoder.encode(newOtp))
                .expiryAt(Instant.now().plus(otpType.getExpiryTimeMinutes(), ChronoUnit.MINUTES)) //!
                .verifyAttempts(otpContext.getVerifyAttempts())
                .resendAttempts(resendAttempt)
                .nextResendAt(Instant.now().plus(OTP_RESEND_COOL_DOWN_SECONDS, ChronoUnit.SECONDS))
                .build();

        this.redisTemplate.opsForValue().set(otpVerificationKey, this.objectMapper.writeValueAsString(newOtpContext), ttl, TimeUnit.SECONDS);
        this.sendOtp(Map.of("otp", newOtp, "expirationTime", otpType.getExpiryTimeMinutes()), identifier);
    }

    @Override
    public String validateOtp(OtpVerificationRequest otpVerificationRequest) {
        String recipient = otpVerificationRequest.identifier();
        String otp = otpVerificationRequest.otp();
        String otpVerificationKey = OTP_VERIFICATION_KEY_PREFIX + recipient;
        String redisValue = this.redisTemplate.opsForValue().get(otpVerificationKey);

        if(redisValue == null) {
            throw new AppException(ErrorCode.OTP_SESSION_EXPIRED);
        }

        OtpContext otpContext = this.objectMapper.readValue(
                redisValue, OtpContext.class
        );

        if(Instant.now().isAfter(otpContext.getExpiryAt())) {
            throw new AppException(ErrorCode.OTP_INVALID_TOKEN);
        }
        if(!this.passwordEncoder.matches(otp, otpContext.getOtp())) {
            int attempt = otpContext.getVerifyAttempts() - 1;
            if(attempt <= 0) {
                this.redisTemplate.delete(otpVerificationKey);
                throw new AppException(ErrorCode.OTP_SESSION_EXPIRED);
            }
            // --- Find better way to solve this problem: (Change property inside but does not affect expiry time)
            Long ttl = this.redisTemplate.getExpire(otpVerificationKey, TimeUnit.SECONDS);

            if(ttl != null && ttl > 0) {
                otpContext.setVerifyAttempts(attempt);

                this.redisTemplate.opsForValue()
                        .set(otpVerificationKey, this.objectMapper.writeValueAsString(otpContext), ttl, TimeUnit.SECONDS);
            }
            // ---
            throw new AppException(ErrorCode.OTP_INVALID_TOKEN);
        }

        this.redisTemplate.delete(otpVerificationKey);

        return this.confirmValidIdentifier(recipient);
    }
}
