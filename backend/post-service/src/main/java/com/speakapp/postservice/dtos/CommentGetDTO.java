package com.speakapp.postservice.dtos;

import com.speakapp.postservice.entities.ReactionType;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class CommentGetDTO {

    UUID commentId;

    String content;

    UserGetDTO author;

    ReactionsGetDTO reactionsGetDTO;

    ReactionType currentUserReactionType;
}
