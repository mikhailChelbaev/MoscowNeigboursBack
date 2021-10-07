package com.moscow.neighbours.backend.db.model.entities;

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
public class DBLocationCoordinate {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    private Double lat;

    private Double lng;
}
