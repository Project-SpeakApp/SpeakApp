package com.speakapp.blobservice.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.speakapp.blobservice.entities.TypeMedia;
import com.speakapp.blobservice.entities.Metadata;
import com.speakapp.blobservice.repositories.MediaMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {
    @Value("${azure.storage.container.name}")
    private String containerName;
    private final BlobContainerClient blobContainerClient;
    private final MediaMetadataRepository mediaMetadataRepository;

    public byte[] downloadMediaByUUID(UUID mediaId, TypeMedia typeMedia) throws IOException {
        Metadata metadata = mediaMetadataRepository.findByMediaId(mediaId, typeMedia);
        BlobClient blobClient = blobContainerClient.getBlobClient(metadata.getMediaId().toString());
        return blobClient.downloadContent().toBytes();
    }

    public String uploadMedia(MultipartFile file, TypeMedia typeMedia, UUID userId) throws IOException {
        UUID mediaId = UUID.randomUUID();
        String fileName = mediaId + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
        blobClient.upload(file.getInputStream());

        Metadata metadata = new Metadata();
        metadata.setMediaId(mediaId);
        metadata.setUserId(userId);
        metadata.setTypeMedia(typeMedia);
        metadata.setFileName(fileName);
        metadata.setBlobUrl(blobClient.getBlobUrl());
        metadata.setSize(file.getSize());
        mediaMetadataRepository.save(metadata);
        return blobClient.getBlobUrl();

    }
}
