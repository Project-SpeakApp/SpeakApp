package com.speakapp.postservice.mappers;

import com.speakapp.postservice.dtos.ReactionsGetDTO;
import com.speakapp.postservice.entities.ReactionType;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Map;

@Mapper
public interface ReactionsMapper {

    @Mapping(source = "sumOfReactionsByType", target = "sumOfReactionsByType")
    @Mapping(source = "sumOfReactionsByType", target = "sumOfReactions", qualifiedByName = "sumOfReactionsByTypeToSumOfReactions")
    ReactionsGetDTO toGetDTO(Map<ReactionType, Long> sumOfReactionsByType);

    @Named("sumOfReactionsByTypeToSumOfReactions")
    default Long sumOfReactionsByTypeToSumOfReactions(@NotNull Map<ReactionType, Long> sumOfReactionsByType) {
        return sumOfReactionsByType.values().stream().reduce(0L, Long::sum);
    }
}
