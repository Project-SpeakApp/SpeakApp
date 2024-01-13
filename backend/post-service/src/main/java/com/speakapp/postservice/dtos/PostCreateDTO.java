package com.speakapp.postservice.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class PostCreateDTO {

  @Size(max = 3000, message = "Max content length is 3000")
  String content;
}
