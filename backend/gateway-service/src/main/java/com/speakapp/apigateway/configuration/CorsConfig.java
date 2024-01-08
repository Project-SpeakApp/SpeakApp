package com.speakapp.apigateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                String frontendUrl = "http://localhost:4200";
                String[] allowedMethods = {"GET", "POST", "PUT", "DELETE"};
                String [] allowedHeaders = {"Authorization", "Content-Type", "UserId"};

                registry.addMapping("/api/users/**")
                        .allowedOrigins(
                                frontendUrl)
                        .allowedMethods(allowedMethods)
                        .allowedHeaders(allowedHeaders)
                        .allowCredentials(true)
                        .maxAge(3600);
                registry.addMapping("/api/posts/**")
                        .allowedOrigins(
                                frontendUrl)
                        .allowedMethods(allowedMethods)
                        .allowedHeaders(allowedHeaders)
                        .allowCredentials(true)
                        .maxAge(3600);
                registry.addMapping("/api/comments/**")
                        .allowedOrigins(
                                frontendUrl)
                        .allowedMethods(allowedMethods)
                        .allowedHeaders(allowedHeaders)
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
