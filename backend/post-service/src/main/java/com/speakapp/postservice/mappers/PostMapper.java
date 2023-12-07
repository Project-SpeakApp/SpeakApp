package com.speakapp.postservice.mappers;

import com.speakapp.postservice.dtos.*;
import com.speakapp.postservice.entities.Post;
import com.speakapp.postservice.entities.ReactionType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.UUID;

@Mapper
public interface PostMapper {

    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "postId", ignore = true)
    Post toEntity(PostCreateDTO postCreateDTO, UUID userId);

    PostGetDTO toGetDTO(Post post,
                        UserGetDTO author,
                        ReactionsGetDTO reactions,
                        ReactionType currentUserReaction);
    void updatePostFromPostCreateDTO(PostCreateDTO postCreateDTO, @MappingTarget Post post);
}
