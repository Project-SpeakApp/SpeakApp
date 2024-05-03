package com.chatservice.dtos;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class AppUserPreviewDTO {

  UUID userId;
  String fullName;
  UUID profilePhotoId;

  public static AppUserPreviewDTO empty(UUID userId) {
    return AppUserPreviewDTO.builder()
        .userId(userId)
        .fullName("")
        .profilePhotoId(null)
        .build();
  }
}
