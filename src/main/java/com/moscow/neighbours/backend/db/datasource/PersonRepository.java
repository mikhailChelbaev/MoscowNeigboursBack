package com.moscow.neighbours.backend.db.datasource;

import com.moscow.neighbours.backend.db.model.route.DBPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PersonRepository extends JpaRepository<DBPerson, UUID> {
}
