package com.speakapp.postservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

}
