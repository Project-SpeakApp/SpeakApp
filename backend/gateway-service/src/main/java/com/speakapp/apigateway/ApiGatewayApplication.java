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
                        .uri("lb://POST-SERVICE"))
//                        .uri("http://post-service:8082")) // in future uri(lb:POST-SERVICE)
                .route("user-service", r -> r
                        .path("/api/users/**")
                        .uri("lb://USER-SERVICE"))
//                        .uri("http://user-service:8081")) // in future uri(lb:USER-SERVICE)
                .route("media-service", r -> r
                        .path("/api/media/**")
                        .uri("lb://BLOB-SERVICE")
//                        .uri("http://blob-service:8083")
                )
                .build();
    }
}