package com.speakapp.postservice.dtos;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class CommentCreateDTO {

    @Size(min = 1, max = 500, message = "Content must not be empty, max content length is 500")
    String content;

    UUID postId;
}
