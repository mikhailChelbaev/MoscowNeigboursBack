package com.moscow.neighbours.backend.db.model.achievements;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DBAchievement {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    private String name;

    @Column(columnDefinition="text")
    private String completedDescription;

    @Column(columnDefinition="text")
    private String uncompletedDescription;

    @Setter
    private String completedImageUrl;

    @Setter
    private String uncompletedImageUrl;
}
