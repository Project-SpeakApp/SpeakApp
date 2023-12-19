package com.speakapp.postservice.repositories;

import com.speakapp.postservice.entities.Comment;
import com.speakapp.postservice.entities.CommentReaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentReactionRepository extends JpaRepository<CommentReaction, UUID> {

    List<CommentReaction> getCommentReactionByComment(Comment comment);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM comment_reaction WHERE comment_id = :commentId", nativeQuery = true)
    int deleteByCommentId(@Param("commentId") UUID commentId);
}
