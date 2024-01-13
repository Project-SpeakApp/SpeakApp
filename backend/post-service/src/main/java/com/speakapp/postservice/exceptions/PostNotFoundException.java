package com.speakapp.postservice.exceptions;

import lombok.NoArgsConstructor;

public class PostNotFoundException extends EntityNotFoundException {

  private static final String ENTITY_TYPE = "Post";
  public PostNotFoundException() {
    super(ENTITY_TYPE);
  }
}
