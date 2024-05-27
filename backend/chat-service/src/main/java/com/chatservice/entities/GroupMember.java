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

@Entity(name = "group_member")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "group_member")
public class GroupMember {

    @Id
    @GeneratedValue
    private UUID groupMemberId;

    @Column(nullable = false)
    private UUID userId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "conversationId")
    private Conversation conversation;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant joinedAt;

    private Instant leftAt;

}
