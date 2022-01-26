package com.moscow.neighbours.backend.db.datasource;

import com.moscow.neighbours.backend.db.model.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<DBUser, Long> {
//    Optional<DBUser> findByEmail(@Param("email") String email);

    Optional<DBUser> findByUserId(@Param("userId") String id);
}
