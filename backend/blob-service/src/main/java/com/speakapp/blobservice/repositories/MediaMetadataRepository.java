package com.speakapp.blobservice.repositories;

import com.speakapp.blobservice.entities.TypeMedia;
import com.speakapp.blobservice.entities.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MediaMetadataRepository extends JpaRepository<Metadata, Long> {
    Metadata findByMediaId(UUID mediaId, TypeMedia typeMedia);
}
