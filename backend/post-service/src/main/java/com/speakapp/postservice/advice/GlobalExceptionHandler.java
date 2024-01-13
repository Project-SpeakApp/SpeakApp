package com.speakapp.postservice.advice;

import com.speakapp.postservice.exceptions.AccessDeniedLayerException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidationError(MethodArgumentNotValidException ex){
    String errorMessage = "Data validation error";
    return new ResponseEntity<>(new ApiError(ex, buildValidationErrorsMap(ex, errorMessage)), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = AccessDeniedLayerException.class)
  public ResponseEntity<ApiError> handleValidationError(AccessDeniedLayerException ex){
    return new ResponseEntity<>(new ApiError(ex, buildErrorsMap(ex)), HttpStatus.FORBIDDEN);
  }

  private List<String> buildErrorsMap(Exception ex){
    List<String> errorsList = new ArrayList<>();
    errorsList.add(ex.getMessage());
    return errorsList;
  }

  private List<String> buildValidationErrorsMap(MethodArgumentNotValidException ex, String errorMessage){
    List<String> errorsList = new ArrayList<>();
    errorsList.add(errorMessage);

    List<FieldError> fieldErrors = ex.getFieldErrors();

    for(FieldError fieldError : fieldErrors){
      errorsList.add("Error in field "
          + fieldError.getField() +
          ", rejected value "
          + fieldError.getRejectedValue() +
          ", "
          + fieldError.getDefaultMessage());
    }

    return errorsList;
  }

}
