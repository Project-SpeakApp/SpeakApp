package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "FavouriteList")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "favourite_list")
public class FavouriteList {
    @Id
    @GeneratedValue
    private UUID listId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String name;

}
