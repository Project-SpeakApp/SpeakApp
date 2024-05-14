package com.speakapp.userservice.exceptions;

import org.springframework.http.HttpStatus;

public class FavouriteListCreationException extends ServiceLayerException{

    private static final String DEFAULT_MESSAGE = "Failed to create favourite list.";

    public FavouriteListCreationException() {
        super(DEFAULT_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public FavouriteListCreationException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
