package com.speakapp.userservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Value
@Builder
@Jacksonized
public class AppUserUpdateDTO {

    String firstName;
    String lastName;
    String about;
    LocalDate dateOfBirth;
}
