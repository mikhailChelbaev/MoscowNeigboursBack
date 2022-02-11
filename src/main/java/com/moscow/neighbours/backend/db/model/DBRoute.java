package com.moscow.neighbours.backend.db.model;

import com.moscow.neighbours.backend.db.model.entities.DBPerson;
import com.moscow.neighbours.backend.db.model.entities.DBRoutePurchase;
import com.moscow.neighbours.backend.models.RoutePurchaseStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "db_routes")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DBRoute {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(columnDefinition="text")
    private String description;

    private String duration;

    private String distance;

    @Setter
    private String coverUrl;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DBRoutePurchase purchase;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DBPersonInfo> personInfo;

}
