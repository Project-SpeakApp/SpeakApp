package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity(name = "Comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue
    private UUID commentId;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post postId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column
    private Timestamp editedAt;

    @Column
    private boolean isDeleted;

    @Column
    private String content;

    //   TODO Media service for photos, audio, video
    //    @Lob
    //    private byte[] media;

}
