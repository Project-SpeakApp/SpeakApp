package com.speakapp.apigateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

public class ApiKeyAuthenticationFilter {

    @Value("${app.keycloak-service-key}")
    private String keycloakServiceKey;

    public Mono<AuthorizationDecision> check(
            Mono<Authentication> authentication,
            AuthorizationContext context
    ) {

        String headerName = "x-api-key";
        String apiKey = context.getExchange().getRequest().getHeaders().getFirst(headerName);
        boolean isValidApiKey = isValidApiKey(apiKey);
        return Mono.just(new AuthorizationDecision(isValidApiKey));
    }

    private boolean isValidApiKey(String apiKey) {
        return apiKey != null && apiKey.equals(keycloakServiceKey);
    }
}

