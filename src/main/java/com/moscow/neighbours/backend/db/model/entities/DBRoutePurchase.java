package com.moscow.neighbours.backend.db.model.entities;

import com.moscow.neighbours.backend.models.RoutePurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DBRoutePurchase {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private RoutePurchaseStatus status;
    private String productId;
}
