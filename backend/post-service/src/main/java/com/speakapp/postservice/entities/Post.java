package com.speakapp.postservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private UUID id;


}

