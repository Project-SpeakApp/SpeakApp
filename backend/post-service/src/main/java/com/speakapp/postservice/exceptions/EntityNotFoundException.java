package com.speakapp.postservice.exceptions;

public class EntityNotFoundException extends ServiceException {

  public EntityNotFoundException(String message) {
    super(message);
  }
}
