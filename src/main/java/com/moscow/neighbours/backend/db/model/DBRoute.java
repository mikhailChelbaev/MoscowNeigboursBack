package com.moscow.neighbours.backend.db.model;

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
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_generator")
//    @SequenceGenerator(name = "route_generator", sequenceName = "route_id_seq", allocationSize = 1)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(columnDefinition="text")
    private String description;

    private String duration;

    private String distance;

    private String coverUrl;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DBPersonInfo> personInfo;

}
