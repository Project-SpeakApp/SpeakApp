package com.speakapp.postservice.exceptions;

public class CommentNotFoundLayerException extends EntityNotFoundLayerException {

  private static final String DEFAULT_MESSAGE= "Requested comment could not be found!";

  public CommentNotFoundLayerException() {
    super(DEFAULT_MESSAGE);
  }

  public CommentNotFoundLayerException(String message) {
    super(message);
  }


}
