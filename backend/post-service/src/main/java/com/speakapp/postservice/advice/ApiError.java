package com.speakapp.postservice.advice;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiError {
  private LocalDateTime timestamp;
  private String exceptionName;  // probably temporary, to ease testing

  private String errorMessage;
  private Map<String, String> subErrorsMessages;

  private String debugMessage;

  private ApiError() {
    timestamp = LocalDateTime.now();
  }

  ApiError(Throwable ex, String errorMessage, Map<String,String> subErrorsMessages) {
    this();
    this.exceptionName = ex.getClass().getName();
    this.errorMessage = errorMessage;
    this.subErrorsMessages = subErrorsMessages;
    this.debugMessage = ex.getLocalizedMessage();
  }


}
