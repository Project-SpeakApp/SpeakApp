package com.chatservice.mappers;

import com.chatservice.entities.Conversation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface ConversationMapper {
    @Mapping(target = "isGroupConversation", source = "isGroupConversation")
    @Mapping(target = "conversationId", ignore = true)
    @Mapping(target = "conversationName", ignore = true)
    Conversation toEntity(Boolean isGroupConversation);
}
