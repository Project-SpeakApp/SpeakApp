package com.speakapp.postservice.mappers;


import com.speakapp.postservice.dtos.PostGetDTO;
import com.speakapp.postservice.dtos.PostPageGetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface PostPageMapper {

    @Mapping(target = "currentPage", source = "pageable.pageNumber")
    @Mapping(target = "pageSize", source = "pageable.pageSize")
    PostPageGetDTO toGetDTO(List<PostGetDTO> posts, Pageable pageable, int totalPages);

}
