package com.speakapp.postservice.mappers;

import com.speakapp.postservice.dtos.PostPageGetDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PostPageMapper {

    PostPageGetDTO toDTO()
}
