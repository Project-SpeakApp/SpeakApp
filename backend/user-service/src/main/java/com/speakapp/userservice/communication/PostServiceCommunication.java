package com.speakapp.userservice.communication;

import com.speakapp.userservice.dtos.FavouriteListCreateDTO;
import com.speakapp.userservice.exceptions.FavouriteListCreationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class PostServiceCommunication {

    private final WebClient webClient;

    public PostServiceCommunication() {
        webClient = WebClient.builder()
                .baseUrl("http://post-service")
                .build();
    }

    public void createFavouriteList(UUID userId) {

        FavouriteListCreateDTO favouriteListCreateDTO = FavouriteListCreateDTO.builder()
                .userId(userId)
                .build();

        webClient.post()
                .uri("/api/internal/posts/favouriteList")
                .body(Mono.just(favouriteListCreateDTO), FavouriteListCreateDTO.class)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new FavouriteListCreationException()))
                )
                .bodyToMono(Void.class)
                .block();
    }
}
