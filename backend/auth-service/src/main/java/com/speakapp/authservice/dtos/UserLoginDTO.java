package com.speakapp.authservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class UserLoginDTO {

    @NotBlank
    String email;

    @NotBlank
    String password;
}
