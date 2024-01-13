package com.speakapp.postservice.exceptions;

public class AccessDeniedLayerException extends ServiceLayerException {

  private static final String DEFAULT_MESSAGE= "Unauthorized access: You do not have permission to access this resource!";
  public AccessDeniedLayerException() {
    super(DEFAULT_MESSAGE);
  }

  public AccessDeniedLayerException(String message) {
    super(message);
  }
}
