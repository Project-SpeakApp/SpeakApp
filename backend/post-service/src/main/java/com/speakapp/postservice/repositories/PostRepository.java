package com.speakapp.postservice.repositories;

import com.speakapp.postservice.entities.FavouriteList;
import com.speakapp.postservice.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    Page<Post> findAllByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Post> findByFavouriteListsOrderByCreatedAtDesc(FavouriteList favouriteList, Pageable pageable);
    Page<Post> findAllByUserIdIsInOrderByCreatedAtDesc(List<UUID> userIds, Pageable pageable);
}
