package com.speakapp.authservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class LoginResponseDTO {

    UUID userId;
    String username;
    String accessToken;
    int expiresIn;
    int refreshExpiresIn;
    String refreshToken;
}
