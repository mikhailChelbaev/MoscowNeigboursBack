package com.moscow.neighbours.backend.db.datasource;

import com.moscow.neighbours.backend.db.model.achievements.DBAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AchievementRepository extends JpaRepository<DBAchievement, UUID> {}
