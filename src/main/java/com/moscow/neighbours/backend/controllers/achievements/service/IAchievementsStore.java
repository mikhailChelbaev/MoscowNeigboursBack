package com.moscow.neighbours.backend.controllers.achievements.service;

import com.moscow.neighbours.backend.controllers.achievements.dto.AchievementSectionDto;
import com.moscow.neighbours.backend.controllers.achievements.dto.CompletedAchievementDto;

import java.util.List;

public interface IAchievementsStore {
    void saveAchievement(String email, CompletedAchievementDto dto);
}
