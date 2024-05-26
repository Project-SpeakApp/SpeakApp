package com.chatservice.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ServiceLayerException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;
}