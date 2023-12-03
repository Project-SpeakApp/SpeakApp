package com.speakapp.postservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class PostCreateDTO {

    String content;
}
