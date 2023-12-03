package com.speakapp.postservice.repositories;

import com.speakapp.postservice.entities.Comment;
import com.speakapp.postservice.entities.CommentReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentReactionRepository extends JpaRepository<CommentReaction, UUID> {

    List<CommentReaction> getCommentReactionByComment(Comment comment);
}
