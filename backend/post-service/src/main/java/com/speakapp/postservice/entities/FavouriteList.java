package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity(name = "FavouriteList")
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favourite_list")
public class FavouriteList extends Auditable{
    @Id
    @GeneratedValue
    private UUID listId;

    @Column(nullable = false)
    private UUID userId;

    @ManyToMany
    @JoinTable(
            name = "favourite_list_post",
            joinColumns = @JoinColumn(name = "favourite_list_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<Post> favouritePosts;
}
