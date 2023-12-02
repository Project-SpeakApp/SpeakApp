package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Post extends Auditable{

    @Id
    @GeneratedValue
    private UUID postId;

    @Column(nullable = false)
    private UUID userId;

    @Column
    private boolean isDeleted;

    @Column(nullable = false, length = 3000)
    private String content;

//   TODO Media service for photos, audio, video
//    @Lob
//    private byte[] media;


}

