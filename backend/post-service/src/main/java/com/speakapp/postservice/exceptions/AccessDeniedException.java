package com.speakapp.postservice.exceptions;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends ServiceLayerException {

  private static final String DEFAULT_MESSAGE = "Unauthorized access: You do not have permission to access this resource!";
  private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

  public AccessDeniedException() {
    super(DEFAULT_MESSAGE, HTTP_STATUS);
  }

  public AccessDeniedException(String message) {
    super(message, HTTP_STATUS);
  }
}
