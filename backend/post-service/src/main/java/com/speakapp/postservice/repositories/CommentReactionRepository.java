package com.speakapp.postservice.repositories;

import com.speakapp.postservice.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentReactionRepository extends JpaRepository<CommentReaction, UUID> {

    List<CommentReaction> getCommentReactionByComment(Comment comment);

    @Query("SELECT cr.type FROM CommentReaction cr WHERE cr.comment = :comment AND cr.userId = :userId")
    Optional<ReactionType> findTypeByCommentAndUserId(Comment comment, UUID userId);

    CommentReaction findCommentReactionByCommentAndUserId(Comment comment, UUID userId);


}
