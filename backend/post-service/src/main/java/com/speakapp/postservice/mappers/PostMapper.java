package com.speakapp.postservice.mappers;

import com.speakapp.postservice.dtos.*;
import com.speakapp.postservice.entities.Post;
import com.speakapp.postservice.entities.ReactionType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.UUID;

@Mapper
public interface PostMapper {

    @Mapping(target = "isDeleted", constant = "true")
    @Mapping(target = "postId", ignore = true)
    Post toEntity(PostCreateDTO postCreateDTO, UUID userId);

    PostGetDTO toGetDTO(Post post,
                               UserGetDTO author,
                               Set<CommentGetDTO> comments,
                               ReactionsGetDTO reactions,
                               ReactionType currentUserReaction);

}
