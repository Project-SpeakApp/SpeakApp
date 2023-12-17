package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "postId")
    private Post postId;

}
