package com.speakapp.postservice.repositories;

import com.speakapp.postservice.entities.Post;
import com.speakapp.postservice.entities.PostReaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostReactionRepository extends JpaRepository<PostReaction, UUID> {

    List<PostReaction> findAllByPost(Post post);

    PostReaction findByPostAndUserId(Post post, UUID userId);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM comment WHERE post_id = :postId", nativeQuery = true)
    int deleteByPostId(@Param("postId") UUID postId);
}

