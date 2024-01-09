package com.speakapp.authservice.services;

import com.speakapp.authservice.dtos.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.client-secret}")
    private String clientSecret;
    @Value("${keycloak.realm}")
    private String realmName;


    private final WebClient webClient;

    public LoginResponseDTO login(String email, String password) {

        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/realms/{realm}/protocol/openid-connect/token").build(realmName))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(BodyInserters.fromFormData("grant_type", "password")
                        .with("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("username", email)
                        .with("password", password)
                        .with("scope", "openid"))
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(
                                    new ResponseStatusException(clientResponse.statusCode(), errorBody)
                            )))
                .bodyToMono(LoginResponseDTO.class)
                .block();
    }
}
