package com.speakapp.userservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity(name = "UserFriend")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_friend")
public class UserFriend extends Auditable {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "requester_id", referencedColumnName = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AppUser requester;

    @ManyToOne
    @JoinColumn(name = "addressee_id", referencedColumnName = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AppUser addressee;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FriendStatus status;
}
