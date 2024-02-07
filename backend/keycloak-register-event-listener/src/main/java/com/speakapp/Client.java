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

    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    private static final Logger log = Logger.getLogger(CustomEventListenerProvider.class);

    public static void postService(AppUserDTO appUserDTO) {
        try {

            String requestBody = appUserToBody(appUserDTO);
            log.info(requestBody);
            // Create the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/users"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            // Handle the response
            responseFuture.thenAccept(response -> {
                // Get the response status code
                int statusCode = response.statusCode();
                System.out.println("Response Code: " + statusCode);

                // Get the response body
                String responseBody = response.body();
                System.out.println("Response: " + responseBody);
            }).join(); // Wait for the response

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static String appUserToBody(AppUserDTO appUserDTO) {
        return "{\"firstName\":\"" + appUserDTO.getFirstName() +
                "\", \"lastName\":\"" + appUserDTO.getLastName() +
                "\", \"email\":\"" + appUserDTO.getEmail() +
                "\", \"dateOfBirth\":\"" + appUserDTO.getDateOfBirth() + "\"}";
    }

}
