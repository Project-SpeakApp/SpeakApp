package com.speakapp.postservice.services;

import com.speakapp.postservice.dtos.ReactionsGetDTO;
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
  public ReactionType createUpdatePostReaction(ReactionType reactionType, UUID postId, UUID userId){
    Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Post with provided id does not exist"));
    PostReaction postReactionInDb = postReactionRepository.findPostReactionByPostAndUserId(post, userId);

    if(postReactionInDb == null) {
      postReactionInDb = PostReaction.builder()
          .post(post)
          .userId(userId)
          .type(reactionType)
          .build();
    } else if(reactionType == null){
      postReactionRepository.delete(postReactionInDb);
    } else {
      postReactionInDb.setType(reactionType);
    }
    postReactionRepository.save(postReactionInDb);

    return postReactionInDb.getType();
  }

}
