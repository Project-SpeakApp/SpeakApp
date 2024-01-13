package com.speakapp.postservice.advice;

import java.time.LocalDateTime;
import java.util.List;

public class ApiError {
  private LocalDateTime timestamp;
  private String exceptionName;  // probably temporary, to ease testing
  private List<String> errorMessages;

  private String debugMessage;

  private ApiError() {
    timestamp = LocalDateTime.now();
  }

  ApiError(Throwable ex, List<String> errorMessages) {
    this();
    this.exceptionName = ex.getClass().getName();
    this.errorMessages = errorMessages;
    this.debugMessage = ex.getLocalizedMessage();
  }


}
