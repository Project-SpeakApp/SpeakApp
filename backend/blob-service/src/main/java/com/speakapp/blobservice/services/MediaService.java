package com.speakapp.blobservice.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.speakapp.blobservice.dtos.BlobFileDTO;
import com.speakapp.blobservice.dtos.BlobMetadataDTO;
import com.speakapp.blobservice.entities.Metadata;
import com.speakapp.blobservice.entities.TypeMedia;
import com.speakapp.blobservice.exceptions.AccessDeniedException;
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
        UUID mediaId = UUID.randomUUID();
        String fileName = typeMedia.name() + File.separator + mediaId + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
        blobClient.upload(file.getInputStream());
        // TODO: Add more sophisticated logic for setting metadata
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

    public void deleteMedia(UUID userId, UUID mediaId) {
        // TODO Finish implementing this method after consulting with frontend team
        Metadata metadata = mediaMetadataRepository.findByMediaId(mediaId);
        if (!metadata.getUserId().equals(userId)) {
            throw new AccessDeniedException("User with id = " + userId + " is not allowed to delete media with id = " + mediaId);
        }
        BlobClient blobClient = blobContainerClient.getBlobClient(metadata.getFileName());
        blobClient.delete();
        mediaMetadataRepository.delete(metadata);
    }


    public BlobMetadataDTO updateMedia(MultipartFile file, UUID mediaId, UUID userId) throws IOException {
        // TODO Finish implementing this method after consulting with frontend team
        Metadata metadata = mediaMetadataRepository.findByMediaId(mediaId);
        if (!metadata.getUserId().equals(userId)) {
            throw new AccessDeniedException("User with id = " + userId + " is not allowed to update media with id = " + mediaId);
        }
        BlobClient blobClient = blobContainerClient.getBlobClient(metadata.getFileName());
        blobClient.upload(file.getInputStream(), true);
        metadata.setSize(file.getSize());
        metadata.setModifiedAt(Instant.now());
        Metadata savedMetadata = mediaMetadataRepository.save(metadata);
        return blobMapper.toDTO(savedMetadata, userId);
    }



}
