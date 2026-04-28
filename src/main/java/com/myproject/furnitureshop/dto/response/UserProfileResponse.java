package com.myproject.furnitureshop.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponse {
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate dob;
    private String avatar;
}
