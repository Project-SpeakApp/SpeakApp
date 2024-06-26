package com.chatservice.dtos;

import com.chatservice.entities.MessageType;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class MessageGetDTO {

    UserGetDTO fromUser;

    String content;

    MessageType type;

}
