package com.chatservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class ConversationGetDTO {

    UUID conversationId;

    String conversationName;

    UUID conversationPhotoId;

    boolean isGroupConversation;
}
