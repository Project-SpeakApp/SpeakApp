package com.chatservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Jacksonized
@Builder
public class ChatPreviewPageDTO {

    List<ChatPreviewDTO> chatPreviewDTOS;

    int currentPage;

    int pageSize;

    int totalPages;

}
