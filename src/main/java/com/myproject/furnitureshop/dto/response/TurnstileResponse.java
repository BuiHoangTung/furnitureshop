package com.myproject.furnitureshop.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class TurnstileResponse {
    private boolean success;

    @JsonProperty("challenge_ts")
    private Instant challengeTs;

    private String hostname;

    @JsonProperty("error-codes")
    private List<String> errorCodes;

    private String action;
    private String cdata;
    private Metadata metadata;

    @Getter
    @Setter
    public static class Metadata {
        @JsonProperty("ephemeral_id")
        private String ephemeralId;
    }
}
