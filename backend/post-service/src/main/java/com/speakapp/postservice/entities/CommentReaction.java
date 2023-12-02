package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "CommentReaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "comment_reaction")
public class CommentReaction {

    @Id
    @GeneratedValue
    private UUID commentReactionId;

    @ManyToOne
    @JoinColumn(name = "commentId")
    private Comment comment;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReactionType type;

}
