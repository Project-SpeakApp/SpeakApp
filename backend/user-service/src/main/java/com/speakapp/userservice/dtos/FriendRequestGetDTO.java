package com.speakapp.userservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Jacksonized
@Builder
public class FriendRequestGetDTO {

    UUID id;

    AppUserPreviewDTO requester;

}
