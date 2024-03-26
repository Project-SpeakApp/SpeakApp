package com.speakapp.blobservice.repositories;

import com.speakapp.blobservice.entities.TypeMedia;
import com.speakapp.blobservice.entities.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface MediaMetadataRepository extends JpaRepository<Metadata, Long> {
    Metadata findByMediaId(UUID mediaId);
}
