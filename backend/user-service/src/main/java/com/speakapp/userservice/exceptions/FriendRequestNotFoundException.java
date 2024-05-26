package com.speakapp.userservice.exceptions;

public class FriendRequestNotFoundException extends EntityNotFoundException{

    private static final String MESSAGE = "Friend request doesn't exist!";

    public FriendRequestNotFoundException() {
        super(MESSAGE);
    }
}
