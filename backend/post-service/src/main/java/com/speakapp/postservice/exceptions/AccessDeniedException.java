package com.speakapp.postservice.exceptions;

public class AccessDeniedException extends ServiceLayerException {

  private static final String DEFAULT_MESSAGE = "Unauthorized access: You do not have permission to access this resource!";

  public AccessDeniedException() {
    super(DEFAULT_MESSAGE);
  }

  public AccessDeniedException(String message) {
    super(message);
  }
}
