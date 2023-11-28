package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;
import java.util.Collection;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue
    private UUID postId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column
    private Timestamp editedAt;

    @Column
    private boolean isDeleted;

    @Column(nullable = false)
    private String content;

//   TODO Media service for photos, audio, video
//    @Lob
//    private byte[] media;


}

