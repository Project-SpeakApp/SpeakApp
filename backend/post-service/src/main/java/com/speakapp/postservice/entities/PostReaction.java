package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "PostReaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "post_reaction")
public class PostReaction {
    @Id
    @GeneratedValue
    private UUID reactionId;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReactionType type;

}
