package com.speakapp.blobservice.dtos;

import com.speakapp.blobservice.entities.TypeMedia;

import java.util.UUID;

public class BlobMetadataDTO {
    private UUID mediaId;
    private UUID userId;
    private TypeMedia typeMedia;
    private Long size;
    private String fileName;
    private byte[] content;

    public String getMediaId() {
        return mediaId.toString();
    }
}
