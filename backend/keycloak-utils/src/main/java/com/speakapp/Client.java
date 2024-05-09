package com.speakapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.speakapp.dtos.AppUserDTO;
import org.jboss.logging.Logger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class Client {

    private Client(){}
    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    private static final ObjectMapper objectMapper;
    private static final Logger log = Logger.getLogger(Client.class);
    private static final String SERVICE_KEY;

    static {
        SERVICE_KEY = System.getenv("KC_API_KEY");
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    public static void postService(AppUserDTO appUserDTO) {
        try {

            String requestBody = appUserToBody(appUserDTO);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://api-gateway:8080/api/users"))
                    .header("Content-Type", "application/json")
                    .header("x-api-key", SERVICE_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            responseFuture.thenAccept(
                    response -> log.info("Response status code: " + response.statusCode())
            ).join();

        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
    }

    private static String appUserToBody(AppUserDTO appUserDTO) {

        try {
            return objectMapper.writeValueAsString(appUserDTO);
        } catch (Exception e) {
            log.error("Failed to create json body from appUserDTO.");
            throw new IllegalArgumentException("Failed to read the appUserDto.");
        }
    }

}
