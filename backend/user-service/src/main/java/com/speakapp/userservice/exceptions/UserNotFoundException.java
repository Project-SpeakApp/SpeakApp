package com.speakapp.userservice.exceptions;

public class UserNotFoundException extends EntityNotFoundException {
    private static final String DEFAULT_MESSAGE = "Requested user could not be found!";

    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
