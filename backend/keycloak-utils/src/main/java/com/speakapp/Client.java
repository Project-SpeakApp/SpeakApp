package com.speakapp;

import com.speakapp.dtos.AppUserDTO;
import org.jboss.logging.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class Client {

    private Client(){}
    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    private static final Logger log = Logger.getLogger(Client.class);

    private static String serviceKey;

    static {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("properties")) {
            properties.load(fis);
            serviceKey = properties.getProperty("keycloak-service-key");
        } catch (IOException e) {
            log.error("Failed to load the keycloak secret. Message: " + e.getMessage());
        }
    }

    public static void postService(AppUserDTO appUserDTO) {
        try {

            String requestBody = appUserToBody(appUserDTO);

            log.info("Keycloak service secret: " + serviceKey);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://api-gateway:8080/api/users"))
                    .header("Content-Type", "application/json")
                    .header("x-api-key", serviceKey)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            responseFuture.thenAccept(response -> {
            }).join();

        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
    }

    private static String appUserToBody(AppUserDTO appUserDTO) {
        return "{\"userId\":\"" + appUserDTO.getUserId() +
                "\", \"firstName\":\"" + appUserDTO.getFirstName() +
                "\", \"lastName\":\"" + appUserDTO.getLastName() +
                "\", \"email\":\"" + appUserDTO.getEmail() +
                "\", \"dateOfBirth\":\"" + appUserDTO.getDateOfBirth() + "\"}";
    }

}
