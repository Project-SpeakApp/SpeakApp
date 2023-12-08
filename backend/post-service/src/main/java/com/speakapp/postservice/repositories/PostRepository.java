package com.speakapp.postservice.repositories;

import com.speakapp.postservice.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    Optional<Post> getPostByPostId(UUID postId);

    Page<Post> findAllByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
}
