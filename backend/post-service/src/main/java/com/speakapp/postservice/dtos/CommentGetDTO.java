package com.speakapp.postservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.apache.catalina.User;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class CommentGetDTO {

    UUID commentId;

    String content;

    UserGetDTO author;
}
