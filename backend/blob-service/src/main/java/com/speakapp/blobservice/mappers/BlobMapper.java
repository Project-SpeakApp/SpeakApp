package com.speakapp.blobservice.mappers;

import com.speakapp.blobservice.dtos.BlobMetadataDTO;
import com.speakapp.blobservice.dtos.UploadFileDTO;
import com.speakapp.blobservice.entities.Metadata;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public interface BlobMapper {
    BlobMetadataDTO toDTO(Metadata metadata);
    UploadFileDTO toDTO(MultipartFile file);
}
