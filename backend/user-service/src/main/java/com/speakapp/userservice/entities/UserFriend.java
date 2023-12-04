package com.speakapp.userservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity(name = "UserFriend")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_friend")
public class UserFriend {
    @Id
    @GeneratedValue
    private UUID friendId;

    @ManyToOne
    @JoinColumn(name = "requesterId", referencedColumnName = "userId")
    private AppUser requesterId;

    @ManyToOne
    @JoinColumn(name = "addresseeId", referencedColumnName = "userId")
    private AppUser addresseeId;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FriendStatus status;

}
