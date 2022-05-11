package com.moscow.neighbours.backend.db.datasource;

import com.moscow.neighbours.backend.db.model.user.DBRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<DBRole, Long> {
    Optional<DBRole> findByName(@Param("name") String name);
}
