package com.chatservice.communication;

import com.chatservice.dtos.UserGetDTO;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

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
                .uri("/api/internal/users/{id}", userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(
                                    new ResponseStatusException(clientResponse.statusCode(), errorBody)
                            ));
                })
                .bodyToMono(UserGetDTO.class)
                .block();
    }
}