package com.speakapp.postservice.mappers;

import com.speakapp.postservice.dtos.*;
import com.speakapp.postservice.entities.Post;
import com.speakapp.postservice.entities.ReactionType;
import org.mapstruct.*;

import java.util.UUID;

@Mapper
public interface PostMapper {

    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Post toEntity(PostCreateDTO postCreateDTO, UUID userId);

    PostGetDTO toGetDTO(Post post,
                        UserGetDTO author,
                        ReactionsGetDTO reactions,
                        ReactionType currentUserReaction,
                        Long totalNumberOfComments,
                        boolean favourite);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updatePostFromPostCreateDTO(PostCreateDTO postCreateDTO, @MappingTarget Post post);
}
