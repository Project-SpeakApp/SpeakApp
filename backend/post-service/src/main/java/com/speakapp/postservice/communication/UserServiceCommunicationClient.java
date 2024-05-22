package com.speakapp.postservice.communication;

import com.speakapp.postservice.dtos.UserGetDTO;
import com.speakapp.postservice.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
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

    public List<UUID> getFriendIdsOfUser(UUID userId) {
        return webClient.get()
                .uri("/api/internal/friends/ids/{id}", userId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UUID>>() {})
                .block();
    }
}
