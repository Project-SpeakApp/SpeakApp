package com.chatservice.dtos;

import lombok.Builder;
import lombok.Setter;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
@Setter
public class ConversationGetDTO {

    UUID conversationId;

    String conversationName;

    boolean isGroupConversation;
}
