package com.moscow.neighbours.backend.db.datasource;

import com.moscow.neighbours.backend.db.model.user.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<DBUser, Long> {
    Optional<DBUser> findByUserId(@Param("userId") String id);
}
