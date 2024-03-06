package com.chatservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.UUID;

@Entity(name = "group_member")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "group_member")
public class GroupMember {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "conversationId")
    private Conversation conversation;

    @CreatedDate
    @Column(updatable = false)
    private Instant joinedDatetime;

    @Column(nullable = false)
    private boolean isGroupChat;

    private Instant leftDatetime;

}
