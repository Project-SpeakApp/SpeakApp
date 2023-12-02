package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "Tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tag")
@EqualsAndHashCode
public class Tag {
    @Id
    @GeneratedValue
    private UUID tagId;

    @Column(nullable = false, length = 64)
    private String tagName;

}
