package com.speakapp.postservice.mappers;

import com.speakapp.postservice.dtos.CommentGetDTO;
import com.speakapp.postservice.dtos.CommentPageGetDTO;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper
public interface CommentPageMapper {

    CommentPageGetDTO toGetDTO(List<CommentGetDTO> commentGetDTOS,
                               int firstComment,
                               int lastComment,
                               Long totalComments);
}
