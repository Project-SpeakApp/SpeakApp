package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@Entity(name = "Tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue
    private UUID tagId;

    @Column(nullable = false)
    private String tagName;

}
