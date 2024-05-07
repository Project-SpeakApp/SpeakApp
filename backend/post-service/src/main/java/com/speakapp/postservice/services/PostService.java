package com.speakapp.postservice.services;

import com.speakapp.postservice.communication.UserServiceCommunicationClient;
import com.speakapp.postservice.dtos.*;
import com.speakapp.postservice.entities.*;
import com.speakapp.postservice.exceptions.AccessDeniedException;
import com.speakapp.postservice.exceptions.PostNotFoundException;
import com.speakapp.postservice.mappers.*;
import com.speakapp.postservice.repositories.CommentRepository;
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
public class PostService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final PostReactionRepository postReactionRepository;

    private final PostMapper postMapper;

    private final ReactionsMapper reactionsMapper;

    private final PostPageMapper postPageMapper;

    private final UserServiceCommunicationClient userServiceCommunicationClient;

    public PostGetDTO createPost(PostCreateDTO postCreateDTO, UUID userId) {
        Post savedPost = postRepository.save(postMapper.toEntity(postCreateDTO, userId));

        UserGetDTO author = userServiceCommunicationClient.getUserById(userId);

        ReactionsGetDTO reactionsGetDTO = reactionsMapper.toGetDTO(Collections.emptyMap());


        return postMapper.toGetDTO(savedPost,
                author,
                reactionsGetDTO,
                null,
                0L
        );
    }

    public PostGetDTO updatePost(PostCreateDTO postCreateDTO, UUID postId, UUID userId) {
        Post postToUpdate = postRepository.findById(postId).orElseThrow(() ->
                new PostNotFoundException("Post with id = " + postId + " was not found"));

        UserGetDTO author = userServiceCommunicationClient.getUserById(postToUpdate.getUserId());

        if (!postToUpdate.getUserId().equals(userId))
            throw new AccessDeniedException("Only author of post can update it");

        postMapper.updatePostFromPostCreateDTO(postCreateDTO, postToUpdate);

        Post postUpdated = postRepository.save(postToUpdate);

        ReactionsGetDTO reactionsGetDTO = getReactionsForThePost(postUpdated);

        ReactionType currentUserReactionType = postReactionRepository.findTypeByPostAndUserId(postUpdated, userId).orElse(null);

        Long totalNumberOfComments = commentRepository.countAllByPost(postUpdated);

        return postMapper.toGetDTO(postUpdated,
                author,
                reactionsGetDTO,
                currentUserReactionType,
                totalNumberOfComments);
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

        Post postToDelete = postRepository.findById(postId).orElseThrow(()->
                new PostNotFoundException("Post with id = " + postId + " has not been found"));


        if(!userId.equals(postToDelete.getUserId()))
            throw new AccessDeniedException("Only author of the post can delete it");

        postRepository.delete(postToDelete);
    }

    // TODO: Possible refactoring - create abstract class with ReactionType field which CommentReaction and PostReaction will extend
    // then generalize "getReactionsForTheComment" and "getReactionsForThePost" methods into one function
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

    protected PostPageGetDTO createPostPageGetDTOFromPostPage(Page<Post> postsPage, UUID userId, Pageable page) {
        List<PostGetDTO> postGetDTOS = postsPage.getContent().stream().map(post -> {
            UserGetDTO postAuthor = userServiceCommunicationClient.getUserById(post.getUserId());
            ReactionsGetDTO postReactions = getReactionsForThePost(post);
            ReactionType currentUserReactionType = postReactionRepository.findTypeByPostAndUserId(post, userId).orElse(null);
            Long totalNumberOfComments = commentRepository.countAllByPost(post);

            return postMapper.toGetDTO(
                post,
                postAuthor,
                postReactions,
                currentUserReactionType,
                totalNumberOfComments
            );
        }).toList();

        return postPageMapper.toGetDTO(
            postGetDTOS,
            page,
            postsPage.getTotalPages()
        );
    }

    public Post getPostById(UUID postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if(postOptional.isEmpty()){
            throw new PostNotFoundException("Post with id = " + postId + " was not found");
        }

        return postOptional.get();
    }

}
