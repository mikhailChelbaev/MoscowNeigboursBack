package com.moscow.neighbours.backend.db.model.user;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * DB model for users' roles
 */
@Entity
@Table(name="db_role")
@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class DBRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    private String name;
}
