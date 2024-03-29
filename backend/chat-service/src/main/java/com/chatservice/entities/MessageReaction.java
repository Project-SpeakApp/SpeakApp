package com.chatservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.UUID;

@Entity(name = "message_reaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "message_reaction")
public class MessageReaction {

    @Id
    @GeneratedValue
    private UUID messageReactionId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "messageId")
    private Message message;

    @Column(nullable = false)
    private UUID userId;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReactionType type;

}
