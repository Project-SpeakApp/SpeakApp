package com.chatservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity(name = "message_seen")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "message_seen")
public class MessageSeen {

    @Id
    @GeneratedValue
    private UUID messageSeenId;

    @Column(nullable = false)
    private UUID userId;

    @OneToOne
    @JoinColumn(name = "messageId")
    private Message messageId;
}
