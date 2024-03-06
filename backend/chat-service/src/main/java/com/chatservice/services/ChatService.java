package com.chatservice.services;

import com.chatservice.entities.Conversation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ChatService {
    private List<Conversation> chatSessions = new ArrayList<>();

    private final SimpMessagingTemplate messagingTemplate;

    public void sendMessageToUser(String sessionId, String destination, Object payload) {
        messagingTemplate.convertAndSendToUser(sessionId, destination, payload);
        }

    public void createSession(Conversation conversation) {
        if(!chatSessions.contains(conversation)){
            chatSessions.add(conversation);
        }
    }

}
