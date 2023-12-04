package com.speakapp.userservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "AppUser")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue
    private UUID userId;

    @Column(nullable = false, length = 64)
    private String firstName;

    @Column(nullable = false, length = 64)
    private String lastName;

    @Column(length = 1024)
    private String about;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String password; //Passwords need to be hashed and salted

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private Instant lastOnline;

    // TODO Add media
    //    private String bgPhotoUrl;

    //  TODO Add media
    //   private String profilePhotoUrl;
}

