package com.speakapp.blobservice.dtos;

import com.speakapp.blobservice.entities.TypeMedia;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class BlobMetadataDTO {
    UUID mediaId;
    UUID userId;
    TypeMedia typeMedia;
    Long size;
    String fileName;
    byte[] content;

    public String getMediaId() {
        return mediaId.toString();
    }
}
