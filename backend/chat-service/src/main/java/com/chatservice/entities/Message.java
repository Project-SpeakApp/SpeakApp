package com.chatservice.entities;

import jakarta.persistence.*;
import lombok.*;

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
    private String type;

}
