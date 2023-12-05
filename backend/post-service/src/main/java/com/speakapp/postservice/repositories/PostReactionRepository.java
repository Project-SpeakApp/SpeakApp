package com.speakapp.postservice.repositories;

import com.speakapp.postservice.entities.Post;
import com.speakapp.postservice.entities.PostReaction;
import com.speakapp.postservice.entities.ReactionType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostReactionRepository extends JpaRepository<PostReaction, UUID> {

    List<PostReaction> findAllByPost(Post post);
    Optional<ReactionType> findByPostAndUserId(Post post, UUID userId);
}

