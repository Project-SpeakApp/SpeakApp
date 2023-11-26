package com.speakapp.userservice.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "speakapp_user")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;

    private String lastName;

    // TODO: Add media
//    private String profilePhotoUrl;

    private String about;

    private LocalDate dateOfBirth;

    private String password;

    @Column(unique = true)
    private String email;

    private Instant lastOnline;

    // TODO: Add media
//    private String bgPhotoUrl;

}

