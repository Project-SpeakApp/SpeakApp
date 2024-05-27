package com.chatservice.exceptions;

public class MessageNotFoundException extends EntityNotFoundException{
    private static final String DEFAULT_MESSAGE = "Requested message could not be found!";

    public MessageNotFoundException(){
        super(DEFAULT_MESSAGE);
    }

    public MessageNotFoundException(String message){
        super(message);
    }


}