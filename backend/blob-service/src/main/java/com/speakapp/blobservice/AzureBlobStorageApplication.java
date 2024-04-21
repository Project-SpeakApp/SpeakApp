package com.speakapp.blobservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class AzureBlobStorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(AzureBlobStorageApplication.class, args);
    }
}