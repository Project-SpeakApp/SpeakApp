package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity(name = "PostReaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "post_reaction")
@EqualsAndHashCode
public class PostReaction {
    @Id
    @GeneratedValue
    private UUID reactionId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReactionType type;

}
