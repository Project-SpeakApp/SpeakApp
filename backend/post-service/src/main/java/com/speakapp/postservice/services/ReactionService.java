package com.speakapp.postservice.services;

import com.speakapp.postservice.entities.*;
import com.speakapp.postservice.exceptions.CommentNotFoundException;
import com.speakapp.postservice.exceptions.PostNotFoundException;
import com.speakapp.postservice.repositories.CommentReactionRepository;
import com.speakapp.postservice.repositories.CommentRepository;
import com.speakapp.postservice.repositories.PostReactionRepository;
import com.speakapp.postservice.repositories.PostRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReactionService {
    private final PostReactionRepository postReactionRepository;
    private final PostRepository postRepository;
    private final CommentReactionRepository commentReactionRepository;
    private final CommentRepository commentRepository;
    
    public ReactionType createUpdatePostReaction(ReactionType newReaction, UUID postId, UUID userId){
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new PostNotFoundException(postId));
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
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new CommentNotFoundException("Comment with provided commentId " + commentId + " does not exist"));
        CommentReaction oldReaction = commentReactionRepository.findCommentReactionByCommentAndUserId(comment, userId);

        if (oldReaction == null && newReaction != null) {
            commentRepository.incrementNumberOfReactions(commentId);
            commentReactionRepository.save(CommentReaction.builder()
                    .comment(comment)
                    .userId(userId)
                    .type(newReaction)
                    .build());
        }
        if (oldReaction != null && newReaction == null) {
            commentRepository.decrementNumberOfReactions(commentId);
            commentReactionRepository.delete(oldReaction);
        }
        if (oldReaction != null && newReaction != null) {
            oldReaction.setType(newReaction);
            commentReactionRepository.save(oldReaction);
        }
        return newReaction;
    }

}
