package com.speakapp.postservice.exceptions;

public class PostNotFoundLayerException extends EntityNotFoundLayerException {

  private static final String DEFAULT_MESSAGE = "Requested post could not be found!";
  public PostNotFoundLayerException() {
    super(DEFAULT_MESSAGE);
  }

  public PostNotFoundLayerException(String message){
    super(message);
  }
}
