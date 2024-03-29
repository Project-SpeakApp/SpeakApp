package com.chatservice.controllers;

import com.chatservice.dtos.MessageDTO;
import com.chatservice.dtos.NewPrivateConversationDTO;
import com.chatservice.entities.Conversation;
import com.chatservice.services.ChatService;
import com.chatservice.utils.JwtDecoder;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final JwtDecoder jwtDecoder;
    private static final String AUTH_HEADER_PREFIX = "Bearer ";

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload MessageDTO messageDTO){
        messagingTemplate.convertAndSend("/chat/" + messageDTO.getToUser(), messageDTO);
    }

    @PostMapping("/api/chat")
    @ResponseStatus(HttpStatus.CREATED)
    public Conversation createPrivateConversation(@RequestHeader("Authorization") String authHeader,
                                                  @RequestBody NewPrivateConversationDTO newPrivateConversationDTO){
        String jwtToken = authHeader.replace(AUTH_HEADER_PREFIX, "");
        UUID userId = jwtDecoder.extractUserIdFromJwt(jwtToken);

        return chatService.createPrivateConversation(newPrivateConversationDTO, userId);
    }

}