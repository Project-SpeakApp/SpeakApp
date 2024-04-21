package com.speakapp.postservice.repositories;

import com.speakapp.postservice.entities.Comment;
import com.speakapp.postservice.entities.Post;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.UUID;

@Repository
@EnableTransactionManagement
@Configuration
@EntityScan("com.speakapp.postservice.entities")
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Page<Comment> findAllByPost(Post post, Pageable pageable);

    List<Comment> findAllByPostOrderByCreatedAtDesc(Post post);


    Long countAllByPost(Post post);
}
