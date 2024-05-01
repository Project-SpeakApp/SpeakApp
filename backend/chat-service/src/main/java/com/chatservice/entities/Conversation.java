package com.chatservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "conversation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "conversation")
public class Conversation {

    @Id
    @GeneratedValue
    private UUID conversationId;

    @Column(name = "conversation_name")
    private String conversationName;

    @OneToOne
    @JoinColumn(name = "last_message_id")
    private Message lastMessage;

    @Column(nullable = false)
    private boolean isGroupConversation;

}
