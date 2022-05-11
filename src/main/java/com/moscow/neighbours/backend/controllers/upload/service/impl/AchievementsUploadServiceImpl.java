package com.moscow.neighbours.backend.controllers.upload.service.impl;

import com.moscow.neighbours.backend.controllers.routes.dto.ShortInfo;
import com.moscow.neighbours.backend.controllers.upload.dto.AchievementUploadDto;
import com.moscow.neighbours.backend.controllers.upload.service.interfaces.IAchievementsUploadService;
import com.moscow.neighbours.backend.db.datasource.AchievementRepository;
import com.moscow.neighbours.backend.db.model.DBAchievement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AchievementsUploadServiceImpl implements IAchievementsUploadService {
    private final AchievementRepository achievementRepository;

    @Autowired
    public AchievementsUploadServiceImpl(
            AchievementRepository achievementRepository
    ) {
        this.achievementRepository = achievementRepository;
    }

    @Override
    public void saveAchievements(List<AchievementUploadDto> achievements) {
        var dbAchievements = achievements
                .stream()
                .map(AchievementUploadDtoMapper::map)
                .collect(Collectors.toList());
        achievementRepository.saveAll(dbAchievements);
    }
}

class AchievementUploadDtoMapper {
    private AchievementUploadDtoMapper() {}

    static DBAchievement map(AchievementUploadDto dto) {
        return new DBAchievement(
                Objects.nonNull(dto.id) ? dto.id : UUID.randomUUID(),
                dto.name,
                dto.description,
                null);
    }
}