package com.chatservice.controllers;

import com.chatservice.dtos.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {


    private final SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload MessageDTO messageDTO){
        messagingTemplate.convertAndSend("/topic/" + messageDTO.getConversationId(), messageDTO);
    }

}