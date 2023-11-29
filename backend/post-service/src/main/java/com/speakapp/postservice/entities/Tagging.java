package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "Tagging")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tagging")
public class Tagging {
    @Id
    @GeneratedValue
    private UUID taggingId;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post postId;

    @ManyToOne
    @JoinColumn(name = "tagging")
    private Tag tagId;

}
