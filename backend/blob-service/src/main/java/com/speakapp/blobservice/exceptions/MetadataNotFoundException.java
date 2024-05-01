package com.speakapp.blobservice.exceptions;

import org.springframework.http.HttpStatus;

public class MetadataNotFoundException extends ServiceLayerException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private static final String DEFAULT_MESSAGE = "Metadata could not be found!";
    public MetadataNotFoundException() {
        super(DEFAULT_MESSAGE, HTTP_STATUS);
    }
}
