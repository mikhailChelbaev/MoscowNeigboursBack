package com.moscow.neighbours.backend.db.model;
import com.moscow.neighbours.backend.db.ImagePresentable;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DBAchievement implements ImagePresentable {
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
