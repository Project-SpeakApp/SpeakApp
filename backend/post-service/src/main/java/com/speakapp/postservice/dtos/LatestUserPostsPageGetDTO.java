package com.speakapp.postservice.dtos;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class LatestUserPostsPageGetDTO {
  List<PostGetDTO> posts;
  int currentPage;
  int pageSize;
  int totalPages;

}
