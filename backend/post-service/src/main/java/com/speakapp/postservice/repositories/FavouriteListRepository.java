package com.speakapp.postservice.repositories;

import com.speakapp.postservice.entities.FavouriteList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavouriteListRepository extends JpaRepository<FavouriteList, UUID> {

    Optional<FavouriteList> getFavouriteListByUserId(UUID userId);
}
