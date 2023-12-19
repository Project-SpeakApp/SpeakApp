package com.speakapp.userservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import java.time.LocalDate;

@Value
@Builder
@Jacksonized
public class AppUserGetDTO {

    String firstName;
    String lastName;
    String profilePhotoUrl;
    String bgPhotoUrl;
    String email;
    String about;
    LocalDate dateOfBirth;
    LocalDate createdAt;
}
