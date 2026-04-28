package com.myproject.furnitureshop.service;

import com.myproject.furnitureshop.dto.response.TurnstileResponse;

public interface TurnstileService {
    TurnstileResponse validateToken(String token, String remoteip);
}
