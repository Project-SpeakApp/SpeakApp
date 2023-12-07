package com.speakapp.userservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class AppUserPreviewDTO {

    UUID userId;
    String fullName;
    String profilePhotoUrl;

    public static AppUserPreviewDTO empty(UUID userId) {
        return AppUserPreviewDTO.builder()
                .userId(userId)
                .fullName("")
                .profilePhotoUrl("")
                .build();
    }
}
