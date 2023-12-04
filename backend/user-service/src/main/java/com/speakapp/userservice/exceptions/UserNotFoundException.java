package com.speakapp.userservice.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(UUID uuid) {
        super("User not found for id: " + uuid);
    }
}
