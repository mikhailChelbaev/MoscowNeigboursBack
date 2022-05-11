package com.moscow.neighbours.backend.controllers.achievements.service;

import com.moscow.neighbours.backend.controllers.achievements.dto.AchievementSectionDto;

import java.util.List;

public interface IAchievementsLoader {
    List<AchievementSectionDto> getAchievements(String email);
}
