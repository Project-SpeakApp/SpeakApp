package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "CommentReaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "comment_reaction")
@EqualsAndHashCode
public class CommentReaction {

    @Id
    @GeneratedValue
    private UUID reactionId;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReactionType type;

}
