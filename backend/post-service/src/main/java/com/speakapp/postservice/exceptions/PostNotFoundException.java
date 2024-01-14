package com.speakapp.postservice.exceptions;

public class PostNotFoundException extends EntityNotFoundException {

  private static final String DEFAULT_MESSAGE = "Requested post could not be found!";

  public PostNotFoundException() {
    super(DEFAULT_MESSAGE);
  }

  public PostNotFoundException(String message) {
    super(message);
  }
}
