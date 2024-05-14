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
    private final CommentRepository commentRepository;
    private final PostReactionRepository postReactionRepository;
    private final PostMapper postMapper;
    private final PostPageMapper postPageMapper;
    private final UserServiceCommunicationClient userServiceCommunicationClient;

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
        return createPostPageGetDTOFromPostPage(favouritePosts, userId, page);
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

    private PostPageGetDTO createPostPageGetDTOFromPostPage(Page<Post> postsPage, UUID userId, Pageable page) {
        List<PostGetDTO> postGetDTOS = postsPage.getContent().stream().map(post -> {
            UserGetDTO postAuthor = userServiceCommunicationClient.getUserById(post.getUserId());
            ReactionsGetDTO postReactions = getReactionsForThePost(post);
            ReactionType currentUserReactionType = postReactionRepository.findTypeByPostAndUserId(post, userId).orElse(null);
            Long totalNumberOfComments = commentRepository.countAllByPost(post);
            boolean favourite = isPostFavourite(userId, post);

            return postMapper.toGetDTO(
                    post,
                    postAuthor,
                    postReactions,
                    currentUserReactionType,
                    totalNumberOfComments,
                    favourite
            );
        }).toList();

        return postPageMapper.toGetDTO(
                postGetDTOS,
                page,
                postsPage.getTotalPages()
        );
    }

    private ReactionsGetDTO getReactionsForThePost(Post post) {
        List<PostReaction> postReactions = postReactionRepository.findAllByPost(post);

        Map<ReactionType, Long> sumOfReactionsByType = new EnumMap<>(ReactionType.class);
        for (PostReaction postReaction : postReactions) {
            sumOfReactionsByType.merge(postReaction.getType(), 1L, Long::sum);
        }

        return ReactionsGetDTO.builder()
                .sumOfReactionsByType(sumOfReactionsByType)
                .sumOfReactions(sumOfReactionsByType
                        .values()
                        .stream()
                        .reduce(0L, Long::sum)
                )
                .build();
    }

    private boolean isPostFavourite(UUID userId, Post post) {

        Optional<FavouriteList> favouriteList = favouriteListRepository.getFavouriteListByUserId(userId);

        if(favouriteList.isEmpty()) {       //If it doesn't exist
            return false;
        }

        return favouriteList.get().getFavouritePosts().contains(post);
    }
}
