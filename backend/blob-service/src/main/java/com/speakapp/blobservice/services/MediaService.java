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
import com.speakapp.blobservice.utils.MultipartFileValidator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.boot.MetadataBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public BlobMetadataDTO uploadMedia(MultipartFile file, TypeMedia typeMedia, UUID userId) throws IOException {

        MultipartFileValidator.validateMultipartFile(file);

        Metadata metadata = Metadata.builder()
                .typeMedia(typeMedia)
                .userId(userId)
                .createdAt(Instant.now())
                .size(file.getSize())
                .build();

        Metadata savedMetadata = mediaMetadataRepository.save(metadata);

        UUID mediaId = savedMetadata.getMediaId();

        String fileName = typeMedia.name() + File.separator + mediaId + "."
                + FilenameUtils.getExtension(file.getOriginalFilename());
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
        blobClient.upload(file.getInputStream());

        savedMetadata.setFileName(fileName);
        savedMetadata.setBlobUrl(blobClient.getBlobUrl());

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
}
