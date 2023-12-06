package com.speakapp.userservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Value
@Builder
@Jacksonized
public class AppUserDTO {

    String firstName;
    String lastName;
    String profilePhotoUrl;
    String bgPhotoUrl;
    String email;
    String about;
    LocalDate dateOfBirth;
}
