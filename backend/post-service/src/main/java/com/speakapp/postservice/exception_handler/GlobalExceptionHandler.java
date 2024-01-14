package com.speakapp.postservice.exception_handler;

import com.speakapp.postservice.exceptions.AccessDeniedException;
import com.speakapp.postservice.exceptions.EntityNotFoundException;
import com.speakapp.postservice.exceptions.ServiceLayerException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    String mainErrorMessage = "Data validation error";

    return new ResponseEntity<>(
        new ApiError(ex, buildValidationErrorMessagesArray(ex.getFieldErrors(), mainErrorMessage)),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = ServiceLayerException.class)
  public ResponseEntity<ApiError> handleServiceLayerExceptionException(ServiceLayerException ex) {
    HttpStatus httpStatus;

    if (ex instanceof AccessDeniedException) {
      httpStatus = HttpStatus.FORBIDDEN;
    } else if (ex instanceof EntityNotFoundException) {
      httpStatus = HttpStatus.NOT_FOUND;
    } else {
      httpStatus = HttpStatus.BAD_REQUEST;
    }

    return new ResponseEntity<>(new ApiError(ex, buildErrorMessagesArray(ex.getMessage())),
        httpStatus);
  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ApiError> handleUnexpectedException(Exception ex) {
    String mainErrorMessage = "Unexpected error";

    return new ResponseEntity<>(new ApiError(ex, buildErrorMessagesArray(mainErrorMessage)),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private String[] buildErrorMessagesArray(String mainErrorMessage) {
    List<String> errorMessagesList = new ArrayList<>();
    errorMessagesList.add(mainErrorMessage);

    return errorMessagesList.toArray(new String[0]);
  }

  private String[] buildValidationErrorMessagesArray(List<FieldError> fieldErrors,
      String mainErrorMessage) {
    List<String> errorMessagesList = new ArrayList<>();
    errorMessagesList.add(mainErrorMessage);

    for (FieldError fieldError : fieldErrors) {
      errorMessagesList.add("Error in field ["
          + fieldError.getField()
          + "] - "
          + fieldError.getDefaultMessage());
    }

    return errorMessagesList.toArray(new String[0]);
  }

}
