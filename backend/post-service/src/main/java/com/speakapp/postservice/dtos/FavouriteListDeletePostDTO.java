package com.speakapp.postservice.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class FavouriteListDeletePostDTO {

    UUID postId;
}
