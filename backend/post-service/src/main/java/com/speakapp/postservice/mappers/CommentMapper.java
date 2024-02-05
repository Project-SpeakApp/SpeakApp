package com.speakapp.postservice.mappers;

import com.speakapp.postservice.dtos.CommentGetDTO;
import com.speakapp.postservice.dtos.ReactionsGetDTO;
import com.speakapp.postservice.dtos.UserGetDTO;
import com.speakapp.postservice.entities.Comment;
import com.speakapp.postservice.entities.Post;
import com.speakapp.postservice.entities.ReactionType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface CommentMapper {

    CommentGetDTO toGetDTO(Comment comment, UserGetDTO author, ReactionsGetDTO reactionsGetDTO, ReactionType currentUserReactionType);

    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "commentId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Comment toEntity(String content, Post post, UUID userId);

}
