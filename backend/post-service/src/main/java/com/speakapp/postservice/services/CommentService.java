package com.speakapp.postservice.services;

import com.speakapp.postservice.communication.UserServiceCommunicationClient;
import com.speakapp.postservice.dtos.*;
import com.speakapp.postservice.entities.Comment;
import com.speakapp.postservice.entities.CommentReaction;
import com.speakapp.postservice.entities.Post;
import com.speakapp.postservice.entities.ReactionType;
import com.speakapp.postservice.exceptions.AccessDeniedException;
import com.speakapp.postservice.exceptions.CommentNotFoundException;
import com.speakapp.postservice.mappers.CommentMapper;
import com.speakapp.postservice.mappers.CommentPageMapper;
import com.speakapp.postservice.mappers.ReactionsMapper;
import com.speakapp.postservice.repositories.CommentReactionRepository;
import com.speakapp.postservice.repositories.CommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    private final EntityManager entityManager;

    public CommentGetDTO updateComment(CommentUpdateDTO commentUpdateDTO, UUID commentId, UUID userId) {
        Comment commentToUpdate = commentRepository.findById(commentId).orElseThrow(() ->
                new CommentNotFoundException("Comment with id = " + commentId + " was not found"));

        UserGetDTO author = userServiceCommunicationClient.getUserById(commentToUpdate.getUserId());

        if (!commentToUpdate.getUserId().equals(userId)) {
            throw new AccessDeniedException("Only author of comment can update it");
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

    public CommentPageGetDTO getCommentsForPost(int firstComment, int lastComment, UUID postId, UUID userId,
                                                String sortBy, Sort.Direction sortDirection) {

        Post post = postService.getPostById(postId);
        List<Comment> sortedPostComments = findCommentPageByPostSorted(post, firstComment, lastComment, sortBy, sortDirection);

        long totalComments = countCommentsByPost(post);

        if (firstComment > totalComments) {
            firstComment = (int) totalComments;
        }

        if (lastComment > totalComments) {
            lastComment = (int) totalComments;
        }

        return createCommentPageGetDTOFromCommentPage(sortedPostComments, userId, firstComment, lastComment, totalComments);

    }

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

    private CommentPageGetDTO createCommentPageGetDTOFromCommentPage(List<Comment> commentsToGet, UUID userId,
                                                                     int firstComment, int lastComment, Long totalComments) {
        List<CommentGetDTO> commentGetDTOS = commentsToGet.stream().map(comment -> {
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
                firstComment,
                lastComment,
                totalComments
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

    public void deleteComment(UUID userId, UUID commentId) {

        Comment commentToDelete = commentRepository.findById(commentId).orElseThrow(() ->
                new CommentNotFoundException("Comment with id = " + commentId + " has not been found"));

        Post commentedPost = commentToDelete.getPost();
        UUID authorOfCommentedPost = commentedPost.getUserId();

        if (!(userId.equals(commentToDelete.getUserId()) || userId.equals(authorOfCommentedPost))) {
            throw new AccessDeniedException("Only author of the post or the comment can delete the comment");
        }

        commentRepository.delete(commentToDelete);
    }

    private List<Comment> findCommentPageByPostSorted(Post post, int firstComment, int lastComment, String sortBy, Sort.Direction sortOrder) {
        String jpqlQuery = "SELECT c FROM Comment c WHERE c.post = :post ORDER BY";

        jpqlQuery += " c." + sortBy + " " + sortOrder;

        Query query = entityManager.createQuery(jpqlQuery);
        query.setParameter("post", post);
        query.setFirstResult(firstComment);
        query.setMaxResults(lastComment - firstComment + 1);

        return query.getResultList();
    }

    private long countCommentsByPost(Post post) {
        String jpqlCountQuery = "SELECT COUNT(c) FROM Comment c WHERE c.post = :post";
        Query countQuery = entityManager.createQuery(jpqlCountQuery);
        countQuery.setParameter("post", post);
        return (long) countQuery.getSingleResult();
    }

}
