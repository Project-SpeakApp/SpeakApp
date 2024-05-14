package com.speakapp.postservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class CommentPageGetDTO {
    List<CommentGetDTO> commentGetDTOS;

    int firstComment;

    int lastComment;

    Long totalComments;
}
