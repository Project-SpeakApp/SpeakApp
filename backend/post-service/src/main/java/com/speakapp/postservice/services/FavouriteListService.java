package com.speakapp.postservice.services;

import com.speakapp.postservice.dtos.PostPageGetDTO;
import com.speakapp.postservice.entities.FavouriteList;
import com.speakapp.postservice.entities.Post;
import com.speakapp.postservice.repositories.FavouriteListRepository;
import com.speakapp.postservice.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavouriteListService {

    private final FavouriteListRepository favouriteListRepository;
    private final PostRepository postRepository;
    private final PostService postService;

    public PostPageGetDTO getFavouritePosts(UUID requesterId, int pageNumber, int pageSize) {

        Pageable page = PageRequest.of(pageNumber, pageSize);
        Optional<FavouriteList> optFavouriteList = favouriteListRepository.getFavouriteListByUserId(requesterId);
        FavouriteList favouriteList = null;

        if(optFavouriteList.isEmpty()) {
            FavouriteList newFavouriteList = FavouriteList.builder()
                    .userId(requesterId)
                    .build();

            favouriteList = favouriteListRepository.save(newFavouriteList);
        } else {
            favouriteList = optFavouriteList.get();
        }

        Page<Post> favouritePosts = postRepository.findByFavouriteLists(favouriteList, page);
        return postService.createPostPageGetDTOFromPostPage(favouritePosts, requesterId, page);
    }
}
