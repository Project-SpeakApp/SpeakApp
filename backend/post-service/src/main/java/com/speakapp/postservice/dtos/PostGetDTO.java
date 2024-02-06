package com.speakapp.postservice.dtos;

import com.speakapp.postservice.entities.ReactionType;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class PostGetDTO {

    UUID postId;

    String content;

    UserGetDTO author;

    Instant createdAt;

    Instant modifiedAt;

    ReactionsGetDTO reactions;

    ReactionType currentUserReaction;

    Long totalNumberOfComments;
}
