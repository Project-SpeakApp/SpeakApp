package com.speakapp.postservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class CommentCreateDTO {

    @NotBlank
    @NotNull
    @Size(min = 1, max = 500, message = "Your comment can't be empty and can have maximally 500 characters")
    String content;

    @NotNull
    UUID postId;
}
