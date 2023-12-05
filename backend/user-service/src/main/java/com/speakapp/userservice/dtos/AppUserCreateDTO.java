package com.speakapp.userservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Value
@Builder
@Jacksonized
public class AppUserCreateDTO {

    String firstName;
    String lastName;
    String email;
    String about;
    String password;
    LocalDate dateOfBirth;
}
