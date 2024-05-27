package com.speakapp.postservice.services;

import com.speakapp.postservice.communication.UserServiceCommunicationClient;
import com.speakapp.postservice.dtos.*;
import com.speakapp.postservice.entities.FavouriteList;
import com.speakapp.postservice.entities.Post;
import com.speakapp.postservice.entities.PostReaction;
import com.speakapp.postservice.entities.ReactionType;
import com.speakapp.postservice.exceptions.FavouriteListNotFoundException;
import com.speakapp.postservice.exceptions.PostNotFoundException;
import com.speakapp.postservice.mappers.PostMapper;
import com.speakapp.postservice.mappers.PostPageMapper;
import com.speakapp.postservice.repositories.CommentRepository;
import com.speakapp.postservice.repositories.FavouriteListRepository;
import com.speakapp.postservice.repositories.PostReactionRepository;
import com.speakapp.postservice.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

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

        Page<Post> favouritePosts = postRepository.findByFavouriteListsOrderByCreatedAtDesc(favouriteList, page);
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
            favouriteListRepository.save(favouriteList);
        }
    }

    public void deletePost(UUID userId, FavouriteListDeletePostDTO favouriteListDeletePostDTO) {

        FavouriteList favouriteList = favouriteListRepository.getFavouriteListByUserId(userId).orElseThrow(
                FavouriteListNotFoundException::new
        );

        UUID postIdToRemove = favouriteListDeletePostDTO.getPostId();

        favouriteList.getFavouritePosts().removeIf(
                favouritePost -> favouritePost.getPostId().equals(postIdToRemove)
        );

        favouriteListRepository.save(favouriteList);
    }

}
