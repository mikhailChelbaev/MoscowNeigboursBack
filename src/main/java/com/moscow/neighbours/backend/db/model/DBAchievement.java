package com.moscow.neighbours.backend.db.model;
import lombok.*;

import javax.persistence.*;
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
    private String description;

    private String date;

    @Setter
    private String imageUrl;
}