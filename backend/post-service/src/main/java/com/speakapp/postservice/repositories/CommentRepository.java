package com.speakapp.postservice.repositories;

import com.speakapp.postservice.entities.Comment;
import com.speakapp.postservice.entities.Post;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@EnableTransactionManagement
@Configuration
@EntityScan("com.speakapp.postservice.entities")
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Long countAllByPost(Post post);

    @Transactional
    @Modifying
    @Query("update Comment c set c.numberOfReactions = c.numberOfReactions + 1 where c.commentId = :id")
    void incrementNumberOfReactions(@Param("id") UUID id);

    @Transactional
    @Modifying
    @Query("update Comment c set c.numberOfReactions = c.numberOfReactions - 1 where c.commentId = :id and c.numberOfReactions > 0")
    void decrementNumberOfReactions(@Param("id") UUID id);
}
