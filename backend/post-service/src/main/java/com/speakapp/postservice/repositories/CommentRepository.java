package com.speakapp.postservice.repositories;

import com.speakapp.postservice.entities.Comment;
import com.speakapp.postservice.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    Page<Comment> findAllByPost(Post post, Pageable pageable);

    List<Comment> findAllByPostOrderByCreatedAtDesc(Post post);
}
