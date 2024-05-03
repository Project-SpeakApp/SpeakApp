package com.speakapp.userservice.communication;

import com.speakapp.userservice.exceptions.UserNotFoundException;
import java.util.UUID;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ChatServiceCommunicationClient {
  private final WebClient webClient;

  public ChatServiceCommunicationClient() {
    webClient = WebClient.builder()
        .baseUrl("http://user-service:8081")
        .build();
  }

  public String updateUserInChatService(UUID userId, String updatedFirstName, String updatedLastName) {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/internal/chat/{userId}")
            .queryParam("updatedFirstName", updatedFirstName)
            .queryParam("updatedLastName", updatedLastName)
            .build(userId))
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
          return clientResponse.bodyToMono(String.class)
              .flatMap(errorBody -> Mono.error(
                  new UserNotFoundException()
              ));
        })
        .bodyToMono(String.class)
        .block();
  }
}
