package com.speakapp.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
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
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("post-service", r -> r
                        .path("/api/posts/**", "/api/comments/**")
                        .uri("http://post-service:8082")) // in future uri(lb:POST-SERVICE)
                .route("user-service", r -> r
                        .path("/api/users/**")
                        .uri("http://user-service:8081")) // in future uri(lb:USER-SERVICE)
                .build();
    }
}