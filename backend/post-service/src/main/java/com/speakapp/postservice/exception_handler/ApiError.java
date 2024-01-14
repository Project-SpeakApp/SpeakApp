package com.speakapp.postservice.exception_handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ApiError {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;
  private String exceptionClassName;
  private String[] errorMessages;

  private ApiError() {
    timestamp = LocalDateTime.now();
  }

  ApiError(Throwable ex, String[] errorMessages) {
    this();
    this.exceptionClassName = ex.getClass().getName();
    this.errorMessages = errorMessages;
  }


}
