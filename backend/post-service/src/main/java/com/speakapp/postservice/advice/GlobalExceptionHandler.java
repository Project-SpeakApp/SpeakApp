package com.speakapp.postservice.advice;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    String errorMessage = "Validation error";
    return new ResponseEntity<>(new ApiError(ex, errorMessage, buildFieldValidationMap(ex)), HttpStatus.BAD_REQUEST);
  }

  private Map<String, String> buildFieldValidationMap(MethodArgumentNotValidException ex){
    List<FieldError> fieldErrors = ex.getFieldErrors();
    Map<String, String> subErrorsMap = new HashMap<>();

    for(FieldError fieldError : fieldErrors){
      subErrorsMap.put(fieldError.getField(),
          "Rejected value " + fieldError.getRejectedValue() + ", " + fieldError.getDefaultMessage());
    }

    return subErrorsMap;
  }

}
