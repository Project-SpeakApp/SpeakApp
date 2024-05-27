package com.speakapp.postservice.exceptions;

import java.util.UUID;

public class PostNotFoundException extends EntityNotFoundException {

    private static final String DEFAULT_MESSAGE = "Requested post could not be found!";

    public PostNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public PostNotFoundException(UUID id) {
        super("Post with id " + id + " could not be found!");
    }

    public PostNotFoundException(String message) {
        super(message);
    }
}
