package com.chatservice.communication;

import com.chatservice.dtos.UserGetDTO;
import com.chatservice.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
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
                                    new UserNotFoundException()
                            ));
                })
                .bodyToMono(UserGetDTO.class)
                .block();
    }

    public boolean checkIfFriends(UUID userId1, UUID userId2){
        return Boolean.TRUE.equals(webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/users/friends/friend-status")
                        .queryParam("messageSenderId", userId1)
                        .queryParam("messageRecieverId", userId2)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(
                                    new UserNotFoundException()
                            ));
                })
                .bodyToMono(Boolean.class)
                .block());
    }
}