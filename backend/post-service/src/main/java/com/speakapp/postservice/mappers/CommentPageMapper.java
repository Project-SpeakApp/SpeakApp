package com.speakapp.postservice.mappers;

import com.speakapp.postservice.dtos.CommentGetDTO;
import com.speakapp.postservice.dtos.CommentPageGetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Mapper
public interface CommentPageMapper {
    @Mapping(target = "currentPage", source = "pageable.pageNumber")
    @Mapping(target = "pageSize", source = "pageable.pageSize")
    CommentPageGetDTO toGetDTO(List<CommentGetDTO> commentGetDTOS,
                               Pageable pageable,
                               int totalPages,
                               long totalComments);
}
