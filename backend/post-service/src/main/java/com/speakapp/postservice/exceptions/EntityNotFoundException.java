package com.speakapp.postservice.exceptions;

import com.speakapp.springexceptionhandler.ServiceLayerException;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends ServiceLayerException {
  private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
  public EntityNotFoundException(String message) {
    super(message, HTTP_STATUS);
  }
}
