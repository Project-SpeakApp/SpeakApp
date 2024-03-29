package com.speakapp.postservice.exceptions;

public class CommentNotFoundException extends EntityNotFoundException {

  private static final String DEFAULT_MESSAGE = "Requested comment could not be found!";

  public CommentNotFoundException() {
    super(DEFAULT_MESSAGE);
  }

  public CommentNotFoundException(String message) {
    super(message);
  }


}
