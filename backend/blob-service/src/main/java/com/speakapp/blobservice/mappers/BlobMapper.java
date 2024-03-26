package com.speakapp.blobservice.mappers;

import com.speakapp.blobservice.dtos.BlobFileDTO;
import com.speakapp.blobservice.dtos.BlobMetadataDTO;
import com.speakapp.blobservice.entities.Metadata;
import org.mapstruct.*;

import java.util.UUID;

@Mapper
public interface BlobMapper {

    @Mapping(target = "mediaId", source = "metadata.mediaId")
    BlobMetadataDTO toDTO(Metadata metadata, UUID userId);

    @Mapping(target = "content", source = "content")
    BlobFileDTO toFileDTO(Metadata metadata, byte[] content);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updateMetadataFromDTO(BlobMetadataDTO blobMetadataDTO, @MappingTarget Metadata metadata);
}
