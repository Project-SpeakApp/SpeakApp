package com.speakapp.authservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }


    @Value("${keycloak.server-url}")
    private String keycloakUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(keycloakUrl).build();
    }
}
