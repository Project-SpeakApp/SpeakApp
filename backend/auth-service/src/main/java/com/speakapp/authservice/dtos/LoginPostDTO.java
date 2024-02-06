package com.speakapp.authservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class LoginPostDTO {

    @NotBlank
    String email;

    @NotBlank
    String password;
}
