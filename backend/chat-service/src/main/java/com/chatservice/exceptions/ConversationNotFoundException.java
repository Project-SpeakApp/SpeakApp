package com.chatservice.exceptions;

public class ConversationNotFoundException extends EntityNotFoundException{
  private static final String DEFAULT_MESSAGE = "Requested conversation could not be found!";

  public ConversationNotFoundException(){
    super(DEFAULT_MESSAGE);
  }

  public ConversationNotFoundException(String message){
    super(message);
  }


}
