package com.moscow.neighbours.backend.controllers.upload.service.interfaces;

import com.moscow.neighbours.backend.controllers.upload.dto.AchievementUploadDto;

import java.util.List;

public interface IAchievementsUploadService {
    void saveAchievements(List<AchievementUploadDto> achievements);
}
