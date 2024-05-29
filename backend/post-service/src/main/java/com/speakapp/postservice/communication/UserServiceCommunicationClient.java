package com.speakapp.postservice.communication;

import com.speakapp.postservice.dtos.AppUserPreviewInternalDTO;
import com.speakapp.postservice.dtos.UserGetDTO;
import com.speakapp.postservice.exceptions.UserNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserServiceCommunicationClient {

    private final WebClient webClient;

    public UserServiceCommunicationClient() {
        webClient = WebClient.builder()
                .baseUrl("http://user-service")
                .build();
    }

    public Map<UUID, AppUserPreviewInternalDTO> getUsersByTheirIds(Set<UUID> userIds) {
        return webClient.get()
                .uri("/api/internal/users/userIds={ids}", String.join(",", userIds.stream()
                                .map(UUID::toString)
                                .collect(Collectors.toSet())
                        )
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<UUID, AppUserPreviewInternalDTO>>() {
                })
                .block();
    }

    public UserGetDTO getUserById(UUID userId) {
        return webClient.get()
                .uri("/api/internal/users/{id}", userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        clientResponse
                                .bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(
                                        new UserNotFoundException()
                                )))
                .bodyToMono(UserGetDTO.class)
                .block();
    }

    public List<UUID> getFriendIdsOfUser(UUID userId) {
        return webClient.get()
                .uri("/api/internal/friends/ids/{id}", userId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UUID>>() {
                })
                .block();
    }
}
