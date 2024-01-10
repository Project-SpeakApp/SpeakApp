package com.speakapp.authservice.services;

import com.speakapp.authservice.dtos.TokenResponseDTO;
import com.speakapp.authservice.dtos.LoginResponseDTO;
import com.speakapp.authservice.mappers.LoginMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.UUID;

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
    private final JwtDecoder jwtDecoder;
    private final LoginMapper loginMapper;

    public LoginResponseDTO login(String email, String password) {
        TokenResponseDTO tokenResponse = getTokenResponse(email, password);

        String accessToken = tokenResponse.getAccessToken();
        Jwt jwt = jwtDecoder.decode(accessToken);

        String username = (String) jwt.getClaims().get("name");
        String userId = (String) jwt.getClaims().get("sub");

        return loginMapper.mapToLoginResponse(UUID.fromString(userId), username, tokenResponse);
    }

    private TokenResponseDTO getTokenResponse(String email, String password) {
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
                .bodyToMono(TokenResponseDTO.class)
                .block();
    }
}
