package com.speakapp.userservice.entities;

import jakarta.persistence.*;
import lombok.*;


import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "AppUser")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "app_user")
public class AppUser extends Auditable {

    @Id
    private UUID userId;

    @Column(nullable = false, length = 64)
    private String firstName;

    @Column(nullable = false, length = 64)
    private String lastName;

    @Column(length = 1024)
    private String about;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private Instant lastOnline;

    @Column(nullable = true)
    private UUID bgPhotoId;

    @Column(nullable = true)
    private UUID profilePhotoId;

    public void updateLastOnline() {
        this.lastOnline = Instant.now();
    }
}

