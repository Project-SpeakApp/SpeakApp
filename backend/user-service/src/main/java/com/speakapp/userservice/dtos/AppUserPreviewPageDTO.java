package com.speakapp.userservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class AppUserPreviewPageDTO {

    List<AppUserPreviewDTO> userPreviews;

    int currentPage;

    int pageSize;

    int totalPages;
}
