package com.speakapp.blobservice.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.speakapp.blobservice.dtos.BlobFileDTO;
import com.speakapp.blobservice.dtos.BlobMetadataDTO;
import com.speakapp.blobservice.entities.Metadata;
import com.speakapp.blobservice.entities.TypeMedia;
import com.speakapp.blobservice.mappers.BlobMapper;
import com.speakapp.blobservice.repositories.MediaMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {
    private final BlobContainerClient blobContainerClient;
    private final MediaMetadataRepository mediaMetadataRepository;
    private final BlobMapper blobMapper;

    public BlobFileDTO downloadMediaByUUID(UUID mediaId) {
        Metadata metadata = mediaMetadataRepository.findByMediaId(mediaId);
        BlobClient blobClient = blobContainerClient.getBlobClient(metadata.getFileName());
        byte[] content = blobClient.downloadContent().toBytes();
        return blobMapper.toFileDTO(metadata, content);
    }

    public BlobMetadataDTO uploadMedia(MultipartFile file, TypeMedia typeMedia, UUID userId) throws IOException {
        // Upload file to Azure Blob Storage
        UUID mediaId = UUID.randomUUID();
        String fileName = typeMedia.name() + File.separator + mediaId + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
        blobClient.upload(file.getInputStream());
        // Save metadata to database
        // Should be optimized to use a single transaction
        Metadata metadata = new Metadata();
        metadata.setMediaId(mediaId);
        metadata.setTypeMedia(typeMedia);
        metadata.setSize(file.getSize());
        metadata.setFileName(fileName);
        metadata.setUserId(userId);
        metadata.setBlobUrl(blobClient.getBlobUrl());
        metadata.setCreatedAt(Instant.now());
        Metadata savedMetadata = mediaMetadataRepository.save(metadata);
        return blobMapper.toDTO(savedMetadata, userId);
    }
}
