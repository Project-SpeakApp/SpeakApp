package com.speakapp.postservice.mappers;

import com.speakapp.postservice.dtos.CommentGetDTO;
import com.speakapp.postservice.dtos.UserGetDTO;
import com.speakapp.postservice.entities.Comment;
import org.mapstruct.Mapper;

@Mapper
public interface CommentMapper {

    CommentGetDTO toGetDTO(Comment comment, UserGetDTO author);

}
