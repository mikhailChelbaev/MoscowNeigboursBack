package com.moscow.neighbours.backend.db.model.route;

import com.moscow.neighbours.backend.db.model.achievements.DBAchievement;
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
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(columnDefinition="text")
    private String description;

    private String duration;

    private String distance;

    private Integer presentationPosition;

    @Setter
    private String coverUrl;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DBRoutePurchase purchase;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DBPersonInfo> personInfo;

    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DBAchievement achievement;
}
