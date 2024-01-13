package com.speakapp.postservice.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityNotFoundException extends Exception {

  public EntityNotFoundException(String entityType) {
    super("Requested " + entityType + " could note be found!");
  }


}
