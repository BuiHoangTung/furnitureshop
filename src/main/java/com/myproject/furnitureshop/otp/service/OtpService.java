package com.myproject.furnitureshop.otp.service;

import com.myproject.furnitureshop.dto.request.OtpVerificationRequest;
import com.myproject.furnitureshop.enums.OtpType;

public interface OtpService {
    void createAndStoreOtp(String identifier, OtpType otpType);
    String validateOtp(OtpVerificationRequest otpVerificationRequest);
    void resendOtp(String identifier, OtpType otpType);
}
