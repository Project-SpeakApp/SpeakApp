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
public class Tagging {
    @Id
    @GeneratedValue
    private UUID taggingId;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post postId;

    @ManyToOne
    @JoinColumn(name = "tagId")
    private Tag tagId;

}
