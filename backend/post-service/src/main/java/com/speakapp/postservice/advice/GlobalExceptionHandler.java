package com.speakapp.postservice.advice;

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
public class GlobalExceptionHandler{
  // change existing handling method
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
    String mainErrorMessage = "Data validation error";
    return new ResponseEntity<>(new ApiError(ex, buildValidationErrorMessagesArray(ex.getFieldErrors(), mainErrorMessage)), HttpStatus.BAD_REQUEST);
  }
  // create new handle method
  @ExceptionHandler(value = ServiceLayerException.class)
  public ResponseEntity<ApiError> handleServiceLayerError(ServiceLayerException ex){
    return new ResponseEntity<>(new ApiError(ex, buildErrorMessagesArray(ex.getMessage())), HttpStatus.FORBIDDEN);
  }

  // add default handling for any other exception
  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ApiError> handleUnexpectedException(Exception ex){
    String mainErrorMessage = "Unexpected error";
    return new ResponseEntity<>(new ApiError(ex, buildErrorMessagesArray(mainErrorMessage)), HttpStatus.INTERNAL_SERVER_ERROR);
  }
  private String [] buildErrorMessagesArray(String mainErrorMessage){
    List<String> errorMessagesList = new ArrayList<>();
    errorMessagesList.add(mainErrorMessage);
    return errorMessagesList.toArray(new String[0]);
  }

  private String [] buildValidationErrorMessagesArray(List<FieldError> fieldErrors, String mainErrorMessage){
    List<String> errorMessagesList = new ArrayList<>();
    errorMessagesList.add(mainErrorMessage);

    for(FieldError fieldError : fieldErrors){
      errorMessagesList.add("Error in field ["
          + fieldError.getField()
          + "] - "
          + fieldError.getDefaultMessage());
    }

    return errorMessagesList.toArray(new String[0]);
  }

}
