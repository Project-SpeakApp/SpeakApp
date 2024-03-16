package com.speakapp.blobservice.configuration;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureBlobStorageConfiguration {

    @Value("${azure.storage.connection.string}")
    private String connectionString;

    @Value("${azure.storage.container.name}")
    private String containerName;

    @Bean
    public BlobServiceClient getBlobServiceClient() {
        return new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }

    @Bean
    public BlobContainerClient blobContainerClient() {
        return getBlobServiceClient().getBlobContainerClient(containerName);
    }
}
