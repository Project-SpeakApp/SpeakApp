package com.speakapp.postservice.repositories;

import com.speakapp.postservice.entities.Comment;
import com.speakapp.postservice.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    Page<Comment> findAllByPostOrderByCreatedAtDesc(Post post, Pageable pageable);
    Page<Comment> findAllByPostOrderByCreatedAtAsc(Post post, Pageable pageable);

    List<Comment> findAllByPostOrderByCreatedAtDesc(Post post);

    @Query("SELECT c FROM Comment c LEFT JOIN CommentReaction cr ON c.commentId = cr.comment.commentId " +
            "WHERE c.post = :post " +
            "GROUP BY c.commentId " +
            "ORDER BY COUNT(cr) DESC, c.createdAt DESC")
    Page<Comment> findAllByPostOrderByReactionsDesc(@Param("post") Post post, Pageable pageable);
    @Query("SELECT c FROM Comment c LEFT JOIN CommentReaction cr ON c.commentId = cr.comment.commentId " +
            "WHERE c.post = :post " +
            "GROUP BY c.commentId " +
            "ORDER BY COUNT(cr) ASC, c.createdAt ASC")
    Page<Comment> findAllByPostOrderByReactionsAsc(@Param("post") Post post, Pageable pageable);
}
