package com.speakapp.postservice.dtos;

import com.speakapp.postservice.entities.ReactionType;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class PostGetDTO {

    UUID postId;

    String content;

    UserGetDTO author;

    Instant createdAt;

   // List<CommentGetDTO> comments;

    ReactionsGetDTO reactions;

    ReactionType currentUserReaction;
}
