package com.moscow.neighbours.backend.db.model.route;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DBShortInfo {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(length = 1024)
    private String title;

    @Column(length = 1024)
    private String subtitle;
}
