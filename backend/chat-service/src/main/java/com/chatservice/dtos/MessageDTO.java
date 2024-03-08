package com.chatservice.dtos;

import lombok.Data;

@Data
public class MessageDTO {

    private String fromUser;

    private String content;

    private String type;

    private String conversationId;
}
