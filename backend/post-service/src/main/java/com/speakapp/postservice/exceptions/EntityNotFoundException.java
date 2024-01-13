package com.speakapp.postservice.exceptions;

public class EntityNotFoundException extends ServiceLayerException {

  public EntityNotFoundException(String message) {
    super(message);
  }
}
