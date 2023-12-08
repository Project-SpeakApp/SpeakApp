package com.speakapp.postservice.repositories;

import com.speakapp.postservice.entities.Comment;
import com.speakapp.postservice.entities.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findAllByPostOrderByCreatedAtDesc(Post post);

    List<Comment> findAllByPost(Post post);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM comment WHERE post_id = :postId", nativeQuery = true)
    int deleteByPostId(@Param("postId") UUID postId);
}
