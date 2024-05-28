package com.speakapp.postservice.services;

import com.speakapp.postservice.communication.UserServiceCommunicationClient;
import com.speakapp.postservice.dtos.*;
import com.speakapp.postservice.entities.FavouriteList;
import com.speakapp.postservice.entities.Post;
import com.speakapp.postservice.entities.PostReaction;
import com.speakapp.postservice.entities.ReactionType;
import com.speakapp.postservice.exceptions.AccessDeniedException;
import com.speakapp.postservice.exceptions.PostNotFoundException;
import com.speakapp.postservice.mappers.PostMapper;
import com.speakapp.postservice.mappers.PostPageMapper;
import com.speakapp.postservice.mappers.ReactionsMapper;
import com.speakapp.postservice.repositories.CommentRepository;
import com.speakapp.postservice.repositories.FavouriteListRepository;
import com.speakapp.postservice.repositories.PostReactionRepository;
import com.speakapp.postservice.repositories.PostRepository;
import com.speakapp.postservice.services.rabbitmq.FileDeletionProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final PostReactionRepository postReactionRepository;

    private final FavouriteListRepository favouriteListRepository;

    private final PostMapper postMapper;

    private final ReactionsMapper reactionsMapper;

    private final PostPageMapper postPageMapper;

    private final UserServiceCommunicationClient userServiceCommunicationClient;

    private final FileDeletionProducer fileDeletionProducer;

    public PostGetDTO createPost(PostCreateDTO postCreateDTO, UUID userId) {
        Post savedPost = postRepository.save(postMapper.toEntity(postCreateDTO, userId));

        UserGetDTO author = userServiceCommunicationClient.getUserById(userId);

        ReactionsGetDTO reactionsGetDTO = reactionsMapper.toGetDTO(Collections.emptyMap());


        return postMapper.toGetDTO(savedPost,
                author,
                reactionsGetDTO,
                null,
                0L,
                false
        );
    }

    public PostGetDTO updatePost(PostCreateDTO postCreateDTO, UUID postId, UUID userId) {
        Post postToUpdate = postRepository.findById(postId).orElseThrow(() ->
                new PostNotFoundException(postId));


        if (!postToUpdate.getUserId().equals(userId))
            throw new AccessDeniedException("Only author of post can update it");

        postMapper.updatePostFromPostCreateDTO(postCreateDTO, postToUpdate);

        Post postUpdated = postRepository.save(postToUpdate);

        return createPostGetDTOFromPost(userId, postUpdated);
    }

    private PostGetDTO createPostGetDTOFromPost(UUID userId, Post post) {
        UserGetDTO author = userServiceCommunicationClient.getUserById(post.getUserId());
        ReactionsGetDTO reactionsGetDTO = getReactionsForThePost(post);

        ReactionType currentUserReactionType = postReactionRepository.findTypeByPostAndUserId(post, userId).orElse(null);

        Long totalNumberOfComments = commentRepository.countAllByPost(post);

        boolean favourite = isPostFavourite(userId, post);

        return postMapper.toGetDTO(
                post,
                author,
                reactionsGetDTO,
                currentUserReactionType,
                totalNumberOfComments,
                favourite
        );
    }

    public PostPageGetDTO getUsersLatestPosts(int pageNumber, int pageSize, UUID userIdOfProfileOwner, UUID userId) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Post> userPostsPage = postRepository.findAllByUserIdOrderByCreatedAtDesc(userIdOfProfileOwner, page);

        return createPostPageGetDTOFromPostPage(userPostsPage, userId, page);
    }

    public PostPageGetDTO getLatestPosts(int pageNumber, int pageSize, UUID userId) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Post> postsPage = postRepository.findAllByOrderByCreatedAtDesc(page);

        return createPostPageGetDTOFromPostPage(postsPage, userId, page);
    }

    public void deletePost(UUID userId, UUID postId) {
        Post postToDelete = postRepository.findById(postId).orElseThrow(() ->
                new PostNotFoundException(postId));

        if (!userId.equals(postToDelete.getUserId()))
            throw new AccessDeniedException("Only author of the post can delete it");
        fileDeletionProducer.sendFileDeletionMessage(postToDelete.getMediaId());
        postRepository.delete(postToDelete);
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

    public PostPageGetDTO createPostPageGetDTOFromPostPage(Page<Post> postsPage, UUID userId, Pageable page) {
        Set<UUID> userIds = postsPage
                .getContent()
                .stream()
                .map(Post::getUserId)
                .collect(Collectors.toSet());
        Map<UUID, AppUserPreviewInternalDTO> uuidToAppUserPreviewInternal = userServiceCommunicationClient.getUsersByTheirIds(userIds);

        List<PostGetDTO> postGetDTOS = postsPage
                .getContent()
                .stream()
                .map(post -> {
                            AppUserPreviewInternalDTO appUserPreviewInternalDTO = uuidToAppUserPreviewInternal.get(post.getUserId());
                            return postMapper.toGetDTO(
                                    post,
                                    UserGetDTO.builder()
                                            .userId(post.getUserId())
                                            .profilePhotoId(appUserPreviewInternalDTO.getProfilePhotoId())
                                            .fullName(appUserPreviewInternalDTO.getFullName())
                                            .build(),
                                    getReactionsForThePost(post),
                                    postReactionRepository.findTypeByPostAndUserId(post, userId).orElse(null),
                                    commentRepository.countAllByPost(post),
                                    isPostFavourite(userId, post)
                            );
                        }
                )
                .toList();

        return postPageMapper.toGetDTO(
                postGetDTOS,
                page,
                postsPage.getTotalPages()
        );
    }

    public Post getPostById(UUID postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new PostNotFoundException("Post with id = " + postId + " was not found");
        }

        return postOptional.get();
    }

    private boolean isPostFavourite(UUID userId, Post post) {
        Optional<FavouriteList> favouriteList = favouriteListRepository.getFavouriteListByUserId(userId);

        return favouriteList.map(list -> list.getFavouritePosts().contains(post)).orElse(false);

    }

    public PostPageGetDTO getLatestPostsByFriends(int pageNumber, int pageSize, UUID userId) {
        Pageable requestedPageable = Pageable.ofSize(pageSize).withPage(pageNumber);
        List<UUID> friendIdsOfUser = userServiceCommunicationClient.getFriendIdsOfUser(userId);
        Page<Post> postsPage = postRepository.findAllByUserIdIsInOrderByCreatedAtDesc(friendIdsOfUser, requestedPageable);

        return createPostPageGetDTOFromPostPage(postsPage, userId, requestedPageable);
    }
}
