package com.speakapp;

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
    private static final Logger log = Logger.getLogger(Client.class);

    public static void postService(AppUserDTO appUserDTO) {
        try {

            String requestBody = appUserToBody(appUserDTO);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://api-gateway:8080/api/users"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            responseFuture.thenAccept(response -> {
                int statusCode = response.statusCode();
                String responseBody = response.body();
                log.infof("Status code: %d\nResponse body: %s\n", statusCode, responseBody);
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
