package com.chatservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class ChatPreviewDTO {

    ConversationGetDTO conversationGetDTO;

    MessageGetDTO lastMessage;

    List<UserGetDTO> ChatMembers;

}
