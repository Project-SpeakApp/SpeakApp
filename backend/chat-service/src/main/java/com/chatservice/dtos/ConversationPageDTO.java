package com.chatservice.dtos;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class ConversationPageDTO {
  List<ConversationGetDTO> conversationGetDTOS;

  int pageNumber;

  int pageSize;

  int totalPages;
}
