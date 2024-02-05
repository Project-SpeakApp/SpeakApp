package com.speakapp.postservice.services;

import com.speakapp.postservice.communication.UserServiceCommunicationClient;
import com.speakapp.postservice.dtos.*;
import com.speakapp.postservice.entities.Comment;
import com.speakapp.postservice.entities.CommentReaction;
import com.speakapp.postservice.entities.Post;
import com.speakapp.postservice.entities.ReactionType;
import com.speakapp.postservice.mappers.CommentMapper;
import com.speakapp.postservice.mappers.CommentPageMapper;
import com.speakapp.postservice.mappers.ReactionsMapper;
import com.speakapp.postservice.repositories.CommentReactionRepository;
import com.speakapp.postservice.repositories.CommentRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostService postService;
    private final CommentRepository commentRepository;
    private final CommentReactionRepository commentReactionRepository;
    private final CommentMapper commentMapper;
    private final CommentPageMapper commentPageMapper;
    private final ReactionsMapper reactionsMapper;
    private final UserServiceCommunicationClient userServiceCommunicationClient;

    public CommentGetDTO updateComment(CommentUpdateDTO commentUpdateDTO, UUID commentId, UUID userId) {
        Comment commentToUpdate = commentRepository.findById(commentId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with id = " + commentId + " was not found"));

        UserGetDTO author = userServiceCommunicationClient.getUserById(commentToUpdate.getUserId());

        if (!commentToUpdate.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only author of comment can update it");
        }

        commentMapper.updateCommentFromCommentUpdateDTO(commentUpdateDTO, commentToUpdate);
        Comment commentUpdated = commentRepository.save(commentToUpdate);
        ReactionsGetDTO reactionsGetDTO = getReactionsForTheComment(commentUpdated);
        ReactionType currentUserReactionType = commentReactionRepository.findTypeByCommentAndUserId(commentUpdated, userId).orElse(null);


        return commentMapper.toGetDTO(commentUpdated,
                author,
                reactionsGetDTO,
                currentUserReactionType);
    }

    public CommentPageGetDTO getCommentsForPost(int pageNumber, int pageSize, UUID postId, UUID userId,
                                                String sortBy, Sort.Direction sortDirection){

        Post post = postService.getPostById(postId);
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortBy));
        Page<Comment> commentsPage = commentRepository.findAllByPost(post, page);
        return createCommentPageGetDTOFromCommentPage(commentsPage, userId, page);
    }

    // Keep the class implementation for migration to CommentService
    @NotNull
    private List<CommentGetDTO> getAllCommentsForThePost(Post post, UUID userId) {
        List<Comment> comments = commentRepository.findAllByPostOrderByCreatedAtDesc(post);

        List<CommentGetDTO> commentGetDTOS = new ArrayList<>();
        for (Comment comment : comments) {
            // TODO: Performance bottleneck in future - consider getting users from user-service by batches
            UserGetDTO commentAuthor = userServiceCommunicationClient.getUserById(comment.getUserId());
            ReactionsGetDTO reactionsGetDTO = getReactionsForTheComment(comment);
            ReactionType currentUserReactionType = commentReactionRepository.findTypeByCommentAndUserId(comment, userId).orElse(null);

            CommentGetDTO commentGetDTO = commentMapper.toGetDTO(
                    comment,
                    commentAuthor,
                    reactionsGetDTO,
                    currentUserReactionType
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

    private CommentPageGetDTO createCommentPageGetDTOFromCommentPage(Page<Comment> commentsPage, UUID userId, Pageable page){
        List<CommentGetDTO> commentGetDTOS = commentsPage.getContent().stream().map(comment -> {
            UserGetDTO commentAuthor = userServiceCommunicationClient.getUserById(comment.getUserId());
            ReactionsGetDTO commentReactions = getReactionsForTheComment(comment);
            ReactionType currentUserReactionType = commentReactionRepository.findTypeByCommentAndUserId(comment, userId).orElse(null);

            return commentMapper.toGetDTO(
                    comment,
                    commentAuthor,
                    commentReactions,
                    currentUserReactionType
            );
        }).toList();

        return commentPageMapper.toGetDTO(
                commentGetDTOS,
                page,
                commentsPage.getTotalPages(),
                commentsPage.getTotalElements()
        );
    }

    public CommentGetDTO createComment(CommentCreateDTO commentCreateDTO, UUID userId) {

        Post postToBeCommented = postService.getPostById(commentCreateDTO.getPostId());

        UserGetDTO author = userServiceCommunicationClient.getUserById(userId);

        Comment savedComment = commentRepository.save(commentMapper.toEntity(commentCreateDTO.getContent(),
                postToBeCommented,
                userId));

        ReactionsGetDTO reactionsGetDTO = reactionsMapper.toGetDTO(Collections.emptyMap());

        return commentMapper.toGetDTO(savedComment,
                author,
                reactionsGetDTO,
                null);

    }

    public void deleteComment(UUID userId, UUID commentId){

        Comment commentToDelete = commentRepository.findById(commentId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with id = " + commentId + "has not been found"));

        Post commentedPost = commentToDelete.getPost();
        UUID authorOfCommentedPost = commentedPost.getUserId();

        if(!(userId.equals(commentToDelete.getUserId()) || userId.equals(authorOfCommentedPost))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Only author of the post or the comment can delete the comment");
        }

        commentRepository.delete(commentToDelete);
    }
}
