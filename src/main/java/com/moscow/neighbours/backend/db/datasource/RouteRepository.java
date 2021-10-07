package com.moscow.neighbours.backend.db.datasource;

import com.moscow.neighbours.backend.db.model.DBRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RouteRepository extends JpaRepository<DBRoute, UUID> {
}
