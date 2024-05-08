package com.chatservice.dtos;

import com.chatservice.entities.MessageType;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
//TODO: for group chats we might have to create DTO with 'toUser' -> 'toConversation'
public class MessagePrivateCreateDTO {

    UUID fromUserId;

    UUID toUserId;

    String content;

    MessageType type;

    UUID conversationId;

}
