package com.speakapp.userservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class AppUserGetDTO {

    UUID userId;
    String firstName;
    String lastName;
    UUID profilePhotoId;
    UUID bgPhotoId;
    String email;
    String about;
    LocalDate dateOfBirth;
    LocalDate createdAt;
}
