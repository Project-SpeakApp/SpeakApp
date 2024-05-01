package com.speakapp.blobservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class BlobFileDTO {

    byte[] content;
}
