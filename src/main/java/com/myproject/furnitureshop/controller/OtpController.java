package com.myproject.furnitureshop.controller;

import com.myproject.furnitureshop.dto.request.OtpVerificationRequest;
import com.myproject.furnitureshop.dto.request.ResendOtpRequest;
import com.myproject.furnitureshop.dto.response.BaseResponse;
import com.myproject.furnitureshop.dto.response.SuccessResponse;
import com.myproject.furnitureshop.enums.OtpType;
import com.myproject.furnitureshop.otp.service.OtpService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/otp")
public class OtpController {
    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/verification")
    public ResponseEntity<BaseResponse> verifyOtp(@Valid @RequestBody OtpVerificationRequest request) {
        String uuid = this.otpService.validateOtp(request);

        SuccessResponse<String> response = SuccessResponse.of("Otp is correct.", uuid);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/resending")
    public ResponseEntity<BaseResponse> resendOtp(@Valid @RequestBody ResendOtpRequest resendOtpRequest) {
        this.otpService.resendOtp(resendOtpRequest.identifier(), OtpType.SIGNUP);

        SuccessResponse<String> response = SuccessResponse.of("Successful OTP verification", "oke");

        return ResponseEntity.ok().body(response);
    }
}
