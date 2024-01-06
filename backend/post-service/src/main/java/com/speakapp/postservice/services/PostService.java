package com.speakapp.postservice.services;

import com.speakapp.postservice.communication.UserServiceCommunicationClient;
import com.speakapp.postservice.dtos.*;
import com.speakapp.postservice.entities.*;
import com.speakapp.postservice.mappers.CommentMapper;
import com.speakapp.postservice.mappers.PostMapper;
import com.speakapp.postservice.mappers.PostPageMapper;
import com.speakapp.postservice.mappers.ReactionsMapper;
import com.speakapp.postservice.repositories.CommentReactionRepository;
import com.speakapp.postservice.repositories.CommentRepository;
import com.speakapp.postservice.repositories.PostReactionRepository;
import com.speakapp.postservice.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final PostReactionRepository postReactionRepository;

    private final CommentReactionRepository commentReactionRepository;

    private final PostMapper postMapper;

    private final CommentMapper commentMapper;

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
                null
        );
    }

    public PostGetDTO updatePost(PostCreateDTO postCreateDTO, UUID postId, UUID userId) {
        Post postToUpdate = postRepository.findById(postId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id = " + postId + " was not found"));

        UserGetDTO author = userServiceCommunicationClient.getUserById(postToUpdate.getUserId());

        if (!postToUpdate.getUserId().equals(userId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only author of post can update it");

        postMapper.updatePostFromPostCreateDTO(postCreateDTO, postToUpdate);

        Post postUpdated = postRepository.save(postToUpdate);

        ReactionsGetDTO reactionsGetDTO = getReactionsForThePost(postUpdated);

        ReactionType currentUserReactionType = postReactionRepository.findTypeByPostAndUserId(postUpdated, userId).orElse(null);


        return postMapper.toGetDTO(postUpdated,
                author,
                reactionsGetDTO,
                currentUserReactionType);
    }

    public PostPageGetDTO getUsersLatestPosts(int pageNumber, int pageSize, UUID userIdOfProfileOwner, UUID userId) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Post> userPostsPage = postRepository.findAllByUserIdOrderByCreatedAtDesc(userIdOfProfileOwner, page);

        return createPostPageGetDTOFromPostPage(userPostsPage, userId, page);
    }

    public ReactionsGetDTO createUpdatePostReaction(ReactionType reactionType, UUID postId, UUID userId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Post with provided id does not exist"));
        PostReaction postReaction = postReactionRepository.findPostReactionByPostAndUserId(post, userId);

        if(postReaction != null) {
            postReaction.setType(reactionType);
        } else {
            postReaction = PostReaction.builder()
                .post(post)
                .userId(userId)
                .type(reactionType)
                .build();
        }
        postReactionRepository.save(postReaction);

        return getReactionsForThePost(post);
    }

    public PostPageGetDTO getLatestPosts(int pageNumber, int pageSize, UUID userId){
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Post> postsPage = postRepository.findAllByOrderByCreatedAtDesc(page);

        return createPostPageGetDTOFromPostPage(postsPage, userId, page);
    }

    public void deletePost(UUID userId, UUID postId){

        Post postToDelete = postRepository.findById(postId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id = " + postId + "has not been found"));


        if(!userId.equals(postToDelete.getUserId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only author of the post can delete it");

        postRepository.delete(postToDelete);
    }

    // Keep the class implementation for migration to CommentService
    @NotNull
    private List<CommentGetDTO> getAllCommentsForThePost(Post post) {
        List<Comment> comments = commentRepository.findAllByPostOrderByCreatedAtDesc(post);

        List<CommentGetDTO> commentGetDTOS = new ArrayList<>();
        for (Comment comment : comments) {
            // TODO: Performance bottleneck in future - consider getting users from user-service by batches
            UserGetDTO commentAuthor = userServiceCommunicationClient.getUserById(comment.getUserId());
            ReactionsGetDTO reactionsGetDTO = getReactionsForTheComment(comment);

            CommentGetDTO commentGetDTO = commentMapper.toGetDTO(
                    comment,
                    commentAuthor,
                    reactionsGetDTO
            );

            commentGetDTOS.add(commentGetDTO);
        }

        return commentGetDTOS;
    }

    // Keep the class implementation for migration to CommentService
    // TODO: Possible refactoring - create abstract class with ReactionType field which CommentReaction and PostReaction will extend
    // then generalize "getReactionsForTheComment" and "getReactionsForThePost" methods into one function
    private ReactionsGetDTO getReactionsForTheComment(Comment comment) {
        List<CommentReaction> commentReactions = commentReactionRepository.getCommentReactionByComment(comment);

        Map<ReactionType, Long> sumOfReactionsByType = new EnumMap<>(ReactionType.class);
        for (CommentReaction commentReaction : commentReactions) {
            sumOfReactionsByType.merge(commentReaction.getType(), 1L, Long::sum);
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

    private PostPageGetDTO createPostPageGetDTOFromPostPage(Page<Post> postsPage, UUID userId, Pageable page){
        List<PostGetDTO> postGetDTOS = postsPage.getContent().stream().map(post -> {
            UserGetDTO postAuthor = userServiceCommunicationClient.getUserById(post.getUserId());
            ReactionsGetDTO postReactions = getReactionsForThePost(post);
            ReactionType currentUserReactionType = postReactionRepository.findTypeByPostAndUserId(post, userId).orElse(null);

            return postMapper.toGetDTO(
                post,
                postAuthor,
                postReactions,
                currentUserReactionType
            );
        }).toList();

        return postPageMapper.toGetDTO(
            postGetDTOS,
            page,
            postsPage.getTotalPages()
        );
    }

}
