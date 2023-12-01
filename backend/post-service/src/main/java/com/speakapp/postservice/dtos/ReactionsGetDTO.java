package com.speakapp.postservice.dtos;

import com.speakapp.postservice.entities.ReactionType;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Value
@Builder
@Jacksonized
public class ReactionsGetDTO {

    Long sumOfReactions;

    Map<ReactionType, Long> sumOfReactionsByType;

}
