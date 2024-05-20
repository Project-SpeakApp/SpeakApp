package com.chatservice.dtos;

import com.chatservice.entities.MessageType;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class MessageGetDTO {

    UserGetDTO fromUser;

    String content;

    MessageType type;

    UUID conversationId;

    Instant sentAt;

}
