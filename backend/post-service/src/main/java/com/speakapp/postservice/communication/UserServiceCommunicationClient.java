package com.speakapp.postservice.communication;

import com.speakapp.postservice.dtos.UserGetDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
public class UserServiceCommunicationClient {

    private final WebClient webClient;

    public UserServiceCommunicationClient() {
        webClient = WebClient.builder()
                .baseUrl("http://user-service:8081")
                .build();
    }

    public UserGetDTO getUserById(UUID userId) {
        return webClient.get()
                .uri("/api/users/{id}", userId)
                .retrieve()
                .bodyToMono(UserGetDTO.class)
                .block();
    }
}
