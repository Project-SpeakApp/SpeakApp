package com.speakapp.authservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class TokenResponseDTO {

    @JsonProperty("access_token")
    @NotNull
    String accessToken;

    @NotNull
    @JsonProperty("expires_in")
    int expiresIn;

    @JsonProperty("refresh_expires_in")
    int refreshExpiresIn;

    @JsonProperty("refresh_token")
    String refreshToken;
}
