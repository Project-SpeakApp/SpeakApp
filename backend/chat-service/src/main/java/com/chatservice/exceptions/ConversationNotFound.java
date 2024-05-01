package com.chatservice.exceptions;

public class ConversationNotFound extends EntityNotFoundException{
  private static final String DEFAULT_MESSAGE = "Requested conversation could not be found!";

  public ConversationNotFound(){
    super(DEFAULT_MESSAGE);
  }

  public ConversationNotFound(String message){
    super(message);
  }


}
