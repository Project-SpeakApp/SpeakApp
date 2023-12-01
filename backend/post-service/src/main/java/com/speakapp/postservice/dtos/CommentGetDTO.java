package com.speakapp.postservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class CommentGetDTO {

    UUID commentId;

    UUID authorId;

    String content;

    String authorFullName;
}
