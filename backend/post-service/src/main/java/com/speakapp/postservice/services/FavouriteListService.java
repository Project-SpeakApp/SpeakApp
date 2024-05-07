package com.speakapp.postservice.services;

import com.speakapp.postservice.dtos.FavouriteListAddPostDTO;
import com.speakapp.postservice.dtos.FavouriteListCreateDTO;
import com.speakapp.postservice.dtos.FavouriteListDeletePostDTO;
import com.speakapp.postservice.dtos.PostPageGetDTO;
import com.speakapp.postservice.entities.FavouriteList;
import com.speakapp.postservice.entities.Post;
import com.speakapp.postservice.exceptions.FavouriteListNotFoundException;
import com.speakapp.postservice.exceptions.PostNotFoundException;
import com.speakapp.postservice.repositories.FavouriteListRepository;
import com.speakapp.postservice.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavouriteListService {

    private final FavouriteListRepository favouriteListRepository;
    private final PostRepository postRepository;
    private final PostService postService;

    public void createFavouriteList(FavouriteListCreateDTO favouriteListCreateDTO) {
        FavouriteList newFavouriteList = FavouriteList.builder()
                .userId(favouriteListCreateDTO.getUserId())
                .build();
        favouriteListRepository.save(newFavouriteList);
    }

    public PostPageGetDTO getFavouritePosts(UUID userId, int pageNumber, int pageSize) {

        Pageable page = PageRequest.of(pageNumber, pageSize);
        FavouriteList favouriteList = favouriteListRepository.getFavouriteListByUserId(userId).orElseThrow(
                FavouriteListNotFoundException::new
        );

        Page<Post> favouritePosts = postRepository.findByFavouriteLists(favouriteList, page);
        return postService.createPostPageGetDTOFromPostPage(favouritePosts, userId, page);
    }

    public void addPost(UUID userId, FavouriteListAddPostDTO favouriteListAddPostDTO) {

        Post post = postRepository.findById(favouriteListAddPostDTO.getPostId()).orElseThrow(
                PostNotFoundException::new
        );

        FavouriteList favouriteList = favouriteListRepository.getFavouriteListByUserId(userId).orElseThrow(
                FavouriteListNotFoundException::new
        );

        List<Post> favouritePosts = favouriteList.getFavouritePosts();

        if(!favouritePosts.contains(post)) {
            favouriteList.getFavouritePosts().add(post);
            favouriteList.setFavouritePosts(favouritePosts);
            favouriteListRepository.save(favouriteList);
        }
    }

    public void deletePost(UUID userId, FavouriteListDeletePostDTO favouriteListDeletePostDTO) {

        FavouriteList favouriteList = favouriteListRepository.getFavouriteListByUserId(userId).orElseThrow(
                FavouriteListNotFoundException::new
        );

        UUID postIdToRemove = favouriteListDeletePostDTO.getPostId();
        List<Post> favouritePosts = favouriteList.getFavouritePosts();
        favouritePosts.removeIf(favouritePost -> favouritePost.getPostId().equals(postIdToRemove));
        favouriteList.setFavouritePosts(favouritePosts);

        favouriteListRepository.save(favouriteList);
    }
}
