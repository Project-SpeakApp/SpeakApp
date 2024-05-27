package com.speakapp.userservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class AppUserPreviewInternalDTO {

    String fullName;
    UUID profilePhotoId;

    public static AppUserPreviewInternalDTO empty() {
        return AppUserPreviewInternalDTO.builder()
                .fullName("")
                .profilePhotoId(null)
                .build();
    }
}
