package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavouriteList {
    @Id
    @GeneratedValue
    private UUID listId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String name;

}
