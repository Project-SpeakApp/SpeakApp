package com.chatservice.dtos;

import com.chatservice.entities.MessageType;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class MessageDTO {

    UUID fromUser;

    UUID toUser;

    String content;

    MessageType type;

    UUID conversationId;
}