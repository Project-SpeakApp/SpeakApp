package com.chatservice.controllers;

import com.chatservice.entities.Conversation;
import com.chatservice.entities.Message;
import com.chatservice.repositories.ConversationRepository;
import com.chatservice.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    private final SimpMessagingTemplate messagingTemplate;

    private final ConversationRepository conversationRepository;


    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload Message message){

    }

}