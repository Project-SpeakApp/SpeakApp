package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "Tagging")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tagging")
@EqualsAndHashCode
public class Tagging {
    @Id
    @GeneratedValue
    private UUID taggingId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

}
