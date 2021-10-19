package com.moscow.neighbours.backend.db.model.entities;

import com.moscow.neighbours.backend.db.model.DBPersonInfo;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DBPerson {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    private String name;

    @Column(columnDefinition="text")
    private String description;

    @Column(columnDefinition="text")
    private String shortDescription;

    @Setter
    private String avatarUrl;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DBShortInfo> info;
}
