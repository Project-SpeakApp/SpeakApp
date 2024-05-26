package com.speakapp.postservice.exceptions;

public class FavouriteListNotFoundException extends EntityNotFoundException {

    private static final String DEFAULT_MESSAGE = "Favourite list could not be found!";

    public FavouriteListNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
    public FavouriteListNotFoundException(String message) {
        super(message);
    }
}
