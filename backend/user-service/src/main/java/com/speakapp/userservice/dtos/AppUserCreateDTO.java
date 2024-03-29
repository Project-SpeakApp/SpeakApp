package com.speakapp.userservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class AppUserCreateDTO {

    UUID userId;
    String firstName;
    String lastName;
    String email;
    LocalDate dateOfBirth;
}
