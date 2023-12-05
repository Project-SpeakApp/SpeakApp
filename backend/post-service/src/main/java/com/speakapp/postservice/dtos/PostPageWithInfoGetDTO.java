package com.speakapp.postservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.domain.Page;

@Value
@Builder
@Jacksonized
public class PostPageWithInfoGetDTO {
  Page<PostGetDTO> postPage;
  int currentPage;
  int pageSize;
  int totalPages;

}
