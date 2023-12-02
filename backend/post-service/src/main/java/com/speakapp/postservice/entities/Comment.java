package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity(name = "Comment")
@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends Auditable {

    @Id
    @GeneratedValue
    private UUID commentId;

    @ManyToOne
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    @Column(nullable = false)
    private UUID userId;

    @Column
    private boolean isDeleted;

    @Column(length = 500)
    private String content;

    //   TODO Media service for photos, audio, video
    //    @Lob
    //    private byte[] media;

}
