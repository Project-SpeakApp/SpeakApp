package com.speakapp.blobservice.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.speakapp.blobservice.dtos.BlobFileDTO;
import com.speakapp.blobservice.dtos.BlobMetadataDTO;
import com.speakapp.blobservice.entities.Metadata;
import com.speakapp.blobservice.entities.TypeMedia;
import com.speakapp.blobservice.exceptions.MetadataNotFoundException;
import com.speakapp.blobservice.mappers.BlobMapper;
import com.speakapp.blobservice.repositories.MediaMetadataRepository;
import com.speakapp.blobservice.utils.MultipartFileValidator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {
    private final BlobContainerClient blobContainerClient;
    private final MediaMetadataRepository mediaMetadataRepository;
    private final BlobMapper blobMapper;

    public BlobFileDTO downloadMediaByUUID(UUID mediaId) {
        Optional<Metadata> optionalMetadata = mediaMetadataRepository.findByMediaId(mediaId);

        Metadata metadata = optionalMetadata.orElseThrow(MetadataNotFoundException::new);

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
}
