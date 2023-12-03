package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity(name = "FavouriteList")
@Data
@SuperBuilder
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

    @Column(nullable = false)
    private String name;

}
