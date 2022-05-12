package com.moscow.neighbours.backend.db.datasource;

import com.moscow.neighbours.backend.db.model.achievements.DBCompletedAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompletedAchievementRepository extends JpaRepository<DBCompletedAchievement, UUID> {}
