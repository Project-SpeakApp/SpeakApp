package com.chatservice.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class NewPrivateConversationDTO {

    UUID conversationCreatorUser;

    UUID conversationMemberUser;

}
