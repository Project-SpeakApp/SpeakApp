package com.chatservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "message")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "message")
public class Message extends Auditable{

    @Id
    @GeneratedValue
    private UUID messageId;

    @Column(nullable = false)
    private UUID fromUser;

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
    private Message responseToMessageId;

    private boolean isDeleted;

}
