package com.speakapp.postservice.services;

import com.speakapp.postservice.communication.UserServiceCommunicationClient;
import com.speakapp.postservice.dtos.*;
import com.speakapp.postservice.entities.*;
import com.speakapp.postservice.mappers.CommentMapper;
import com.speakapp.postservice.mappers.PostMapper;
import com.speakapp.postservice.repositories.CommentReactionRepository;
import com.speakapp.postservice.repositories.CommentRepository;
import com.speakapp.postservice.repositories.PostReactionRepository;
import com.speakapp.postservice.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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

    private final UserServiceCommunicationClient userServiceCommunicationClient;

    public PostGetDTO createPost(PostCreateDTO postCreateDTO, UUID userId) {
        Post savedPost = postRepository.save(postMapper.toEntity(postCreateDTO, userId));

        UserGetDTO author = userServiceCommunicationClient.getUserById(userId);

        // unnecessary but it is for query and jpa test purposes - it will fail if something is wrong in jpa config
        // TODO: delete after complex tests - can be assumed empty (post has been just created)
        List<CommentGetDTO> commentGetDTOS = getAllCommentsForThePost(savedPost);

        // unnecessary but it is for query and jpa test purposes - it will fail if something is wrong in jpa config
        // TODO: delete after complex tests - can be assumed empty (post has been just created)
        ReactionsGetDTO reactionsGetDTO = getReactionsForThePost(savedPost);

        return postMapper.toGetDTO(savedPost,
                author,
                commentGetDTOS,
                reactionsGetDTO,
                null
        );
    }

    public PostGetDTO updatePost(PostCreateDTO postCreateDTO, UUID postId, UUID userId){

        Post postUpdated = postRepository.getPostByPostId(postId);

        if(postUpdated.getUserId() != userId){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        postUpdated.setContent(postCreateDTO.getContent());

        postRepository.save(postUpdated);

        UserGetDTO author = userServiceCommunicationClient.getUserById(userId);

        // unnecessary but it is for query and jpa test purposes - it will fail if something is wrong in jpa config
        // TODO: delete after complex tests - can be assumed empty (post has been just created)
        List<CommentGetDTO> commentGetDTOS = getAllCommentsForThePost(postUpdated);

        // unnecessary but it is for query and jpa test purposes - it will fail if something is wrong in jpa config
        // TODO: delete after complex tests - can be assumed empty (post has been just created)
        ReactionsGetDTO reactionsGetDTO = getReactionsForThePost(postUpdated);

        return postMapper.toGetDTO(postUpdated,
                author,
                commentGetDTOS,
                reactionsGetDTO,
                null
        );

    }

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


}
