package com.speakapp.postservice.services;

import com.speakapp.postservice.communication.UserServiceCommunicationClient;
import com.speakapp.postservice.dtos.CommentGetDTO;
import com.speakapp.postservice.dtos.CommentPageGetDTO;
import com.speakapp.postservice.dtos.ReactionsGetDTO;
import com.speakapp.postservice.dtos.UserGetDTO;
import com.speakapp.postservice.entities.Comment;
import com.speakapp.postservice.entities.CommentReaction;
import com.speakapp.postservice.entities.Post;
import com.speakapp.postservice.entities.ReactionType;
import com.speakapp.postservice.mappers.CommentMapper;
import com.speakapp.postservice.mappers.CommentPageMapper;
import com.speakapp.postservice.repositories.CommentReactionRepository;
import com.speakapp.postservice.repositories.CommentRepository;
import com.speakapp.postservice.repositories.PostRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentReactionRepository commentReactionRepository;
    private final CommentMapper commentMapper;
    private final CommentPageMapper commentPageMapper;
    private final UserServiceCommunicationClient userServiceCommunicationClient;

    public CommentPageGetDTO getCommentsForPost(int pageNumber, int pageSize, UUID postId, UUID userId){
        Optional<Post> postOptional = postRepository.findById(postId);
        if(postOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post with id = " + postId + " was not found");
        }

        Post post = postOptional.get();
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Comment> commentsPage = commentRepository.findAllByPostOrderByCreatedAtDesc(post, page);

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
}
