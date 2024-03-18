package com.speakapp.blobservice.exceptions;

public class BlobNotFoundException extends EntityNotFoundException{
    private static final String DEFAULT_MESSAGE = "Requested blob could not be found!";
    public BlobNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
    public BlobNotFoundException(String message) {
        super(message);
    }

}
