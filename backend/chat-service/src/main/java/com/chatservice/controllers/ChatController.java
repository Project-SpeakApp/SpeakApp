package com.chatservice.controllers;


import com.chatservice.dtos.ChatPreviewPageDTO;
import com.chatservice.dtos.MessageGetDTO;
import com.chatservice.dtos.MessagePrivateCreateDTO;
import com.chatservice.dtos.NewPrivateConversationDTO;
import com.chatservice.entities.Conversation;
import com.chatservice.services.ChatService;
import com.chatservice.utils.JwtDecoder;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
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
    public void sendMessage(@Payload MessagePrivateCreateDTO messageDTO){
        messagingTemplate.convertAndSend("/chat/" + messageDTO.getToUserId(), messageDTO);
        chatService.saveMessage(messageDTO);
    }

    @PostMapping("/api/chat")
    @ResponseStatus(HttpStatus.CREATED)
    public Conversation createPrivateConversation(@RequestHeader("Authorization") String authHeader,
                                                  @RequestBody NewPrivateConversationDTO newPrivateConversationDTO){
        String jwtToken = authHeader.replace(AUTH_HEADER_PREFIX, "");
        UUID userId = jwtDecoder.extractUserIdFromJwt(jwtToken);

        return chatService.createPrivateConversation(newPrivateConversationDTO, userId);
    }

    @GetMapping("/api/chat/chatpreview")
    public ChatPreviewPageDTO getChatPreviewPageDTO(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestHeader("Authorization") String authHeader) {
        String jwtToken = authHeader.replace(AUTH_HEADER_PREFIX, "");
        UUID userId = jwtDecoder.extractUserIdFromJwt(jwtToken);
        return chatService.getChatPreviews(userId, pageNumber, pageSize);
    }

  @GetMapping("/api/chat/{conversationId}")
  @ResponseStatus(HttpStatus.OK)
  public List<MessageGetDTO> getConversationHistory(@RequestParam(defaultValue = "0") int pageNumber,
      @RequestParam(defaultValue = "5") int pageSize,
      @PathVariable UUID conversationId,
      @RequestHeader("Authorization") String authHeader) {
    String jwtToken = authHeader.replace(AUTH_HEADER_PREFIX, "");
    UUID userId = jwtDecoder.extractUserIdFromJwt(jwtToken);

    return chatService.getConversationHistory(pageNumber, pageSize, conversationId, userId);
  }
}