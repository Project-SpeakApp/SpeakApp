package com.speakapp.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
    @Bean
    @LoadBalanced
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("post-service", r -> r
                        .path("/api/posts/**", "/api/comments/**")
                        .uri("http://post-service")) //post-service:8082
                .route("user-service", r -> r
                        .path("/api/users/**")
                        .uri("http://user-service")) //user-service:8081
                .route("media-service", r -> r
                        .path("/api/media/**")
                        .uri("http://blob-service") //blob-service:8083
                )
                .route("chat-service", r -> r
                        .path("/api/chat/**", "/chat.sendMessage")
                        .uri("http://chat-service") //chat-service:8084
                )
                .build();
    }
}