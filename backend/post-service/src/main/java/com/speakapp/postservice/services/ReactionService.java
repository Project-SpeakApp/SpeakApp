package com.speakapp.postservice.services;

import com.speakapp.postservice.entities.Post;
import com.speakapp.postservice.entities.PostReaction;
import com.speakapp.postservice.entities.ReactionType;
import com.speakapp.postservice.repositories.PostReactionRepository;
import com.speakapp.postservice.repositories.PostRepository;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReactionService {
    private final PostReactionRepository postReactionRepository;
    private final PostRepository postRepository;
    
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

}
