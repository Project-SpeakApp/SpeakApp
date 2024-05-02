package com.speakapp.userservice.dtos;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class AppUserPreviewPageDto {

  List<AppUserPreviewDTO> appUserPreviewDTOS;

  int pageNumber;

  int pageSize;

  Long totalPages;
}
