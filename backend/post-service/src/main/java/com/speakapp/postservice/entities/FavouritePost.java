package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "FavouritePost")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "favourite_post")
@EqualsAndHashCode
public class FavouritePost {
    @Id
    @GeneratedValue
    private UUID favPostId;

    @ManyToOne
    @JoinColumn(name = "listId")
    private FavouriteList listId;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post postId;

}
