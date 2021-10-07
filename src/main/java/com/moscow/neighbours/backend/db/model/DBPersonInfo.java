package com.moscow.neighbours.backend.db.model;

import com.moscow.neighbours.backend.db.model.entities.DBLocationCoordinate;
import com.moscow.neighbours.backend.db.model.entities.DBPerson;
import com.moscow.neighbours.backend.db.model.entities.DBPlace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "db_person_info")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DBPersonInfo {
    @Id
    @Column(name = "id", columnDefinition = "uuid")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_info_generator")
//    @SequenceGenerator(name = "person_info_generator", sequenceName = "person_info_id_seq", allocationSize = 1)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DBLocationCoordinate coordinates;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DBPerson person;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DBPlace place;
}
