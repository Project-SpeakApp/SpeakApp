package com.speakapp.postservice.services;

import com.speakapp.postservice.entities.*;
import com.speakapp.postservice.repositories.CommentReactionRepository;
import com.speakapp.postservice.repositories.CommentRepository;
import com.speakapp.postservice.repositories.PostReactionRepository;
import com.speakapp.postservice.repositories.PostRepository;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ReactionService {
    private final PostReactionRepository postReactionRepository;
    private final PostRepository postRepository;
    private final CommentReactionRepository commentReactionRepository;
    private final CommentRepository commentRepository;
    
    public ReactionType createUpdatePostReaction(ReactionType newReaction, UUID postId, UUID userId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Post with provided id does not exist"));
        PostReaction oldReaction = postReactionRepository.findPostReactionByPostAndUserId(post, userId);

        if (oldReaction == null && newReaction != null) {
            postReactionRepository.save(PostReaction.builder()
              .post(post)
              .userId(userId)
              .type(newReaction)
              .build());
        }
        if (oldReaction != null && newReaction == null) {
            postReactionRepository.delete(oldReaction);
        }
        if (oldReaction != null && newReaction != null) {
            oldReaction.setType(newReaction);
            postReactionRepository.save(oldReaction);
        }
        return newReaction;
    }

    public ReactionType createUpdateCommentReaction(ReactionType newReaction, UUID commentId, UUID userId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Comment with provided commentId" + commentId + "does not exist"));
        CommentReaction oldReaction = commentReactionRepository.findCommentReactionByCommentAndUserId(comment, userId);

        if (oldReaction == null && newReaction != null) {
            commentReactionRepository.save(CommentReaction.builder()
                    .comment(comment)
                    .userId(userId)
                    .type(newReaction)
                    .build());
        }
        if (oldReaction != null && newReaction == null) {
            commentReactionRepository.delete(oldReaction);
        }
        if (oldReaction != null && newReaction != null) {
            oldReaction.setType(newReaction);
            commentReactionRepository.save(oldReaction);
        }
        return newReaction;
    }

}
