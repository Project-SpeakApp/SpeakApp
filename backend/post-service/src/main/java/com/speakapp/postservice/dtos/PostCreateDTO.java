package com.speakapp.postservice.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class PostCreateDTO {

  @Size(min = 1, max = 3000, message = "Max content length is 3000")
  String content;
  
  @Nullable
  UUID mediaId;
}
