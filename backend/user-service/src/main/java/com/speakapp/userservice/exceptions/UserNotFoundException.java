package com.speakapp.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class UserNotFoundException extends ResponseStatusException {

    public UserNotFoundException(UUID uuid) {
        super(HttpStatus.NOT_FOUND, "User not found for id: " + uuid);
    }
}
