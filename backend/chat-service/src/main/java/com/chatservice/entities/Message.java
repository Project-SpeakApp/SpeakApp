package com.chatservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@Entity(name = "message")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "message")
public class Message{

    @Id
    @GeneratedValue
    private UUID messageId;

    @Column(nullable = false)
    private UUID fromUserId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "conversationId")
    private Conversation conversation;

    @OneToOne
    @JoinColumn(name = "response_to_message_id")
    private Message responseToMessage;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant sentAt;

    private Instant deliveredAt;

    private boolean isDeleted;

}
