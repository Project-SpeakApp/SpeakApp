package com.speakapp.userservice.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidFriendRequestException extends ServiceLayerException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public InvalidFriendRequestException(String message) {
        super(message, HTTP_STATUS);
    }
}
