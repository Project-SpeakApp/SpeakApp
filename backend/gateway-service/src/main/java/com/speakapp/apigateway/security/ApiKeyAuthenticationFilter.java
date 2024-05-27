package com.speakapp.apigateway.security;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

public class ApiKeyAuthenticationFilter {

    private static final String keycloakServiceKey = System.getenv("KC_API_KEY");

    public static Mono<AuthorizationDecision> check(
            Mono<Authentication> authentication,
            AuthorizationContext context
    ) {

        String headerName = "x-api-key";
        String apiKey = context.getExchange().getRequest().getHeaders().getFirst(headerName);
        boolean isValidApiKey = isValidApiKey(apiKey);
        return Mono.just(new AuthorizationDecision(isValidApiKey));
    }

    private static boolean isValidApiKey(String apiKey) {
        return apiKey != null && apiKey.equals(keycloakServiceKey);
    }
}

