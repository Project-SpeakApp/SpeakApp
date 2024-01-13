package com.speakapp.postservice.exceptions;

import java.util.UUID;

public class CommentNotFoundException extends EntityNotFoundException {

  private static final String ENTITY_TYPE = "Comment";

  public CommentNotFoundException() {
  super(ENTITY_TYPE);
  }
}
