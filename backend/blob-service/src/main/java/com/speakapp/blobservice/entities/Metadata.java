package com.speakapp.blobservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.UUID;

@Entity(name = "metadata")
@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Metadata extends Auditable {

    @Id
    @GeneratedValue
    private UUID mediaId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeMedia typeMedia;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private String blobUrl;

    @Column(nullable = false)
    private String fileName;

}
