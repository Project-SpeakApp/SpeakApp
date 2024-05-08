package com.chatservice.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidJWTFormatException extends ServiceLayerException{

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    public InvalidJWTFormatException(String message) {
        super(message, HTTP_STATUS);
    }
}