package com.moscow.neighbours.backend.controllers.upload.service.impl;

import com.moscow.neighbours.backend.controllers.upload.dto.AchievementUploadDto;
import com.moscow.neighbours.backend.controllers.upload.service.interfaces.IAchievementsUploadService;
import com.moscow.neighbours.backend.db.datasource.AchievementRepository;
import com.moscow.neighbours.backend.db.datasource.RouteRepository;
import com.moscow.neighbours.backend.db.model.achievements.DBAchievement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AchievementsUploadServiceImpl implements IAchievementsUploadService {
    private final AchievementRepository achievementRepository;
    private final RouteRepository routeRepository;

    @Autowired
    public AchievementsUploadServiceImpl(
            AchievementRepository achievementRepository,
            RouteRepository routeRepository) {
        this.achievementRepository = achievementRepository;
        this.routeRepository = routeRepository;
    }

    @Override
    public void saveAchievements(List<AchievementUploadDto> achievements) {
        var pairs = achievements
                .stream()
                .map(dto -> new AchievementsPair(dto, AchievementUploadDtoMapper.map(dto)))
                .collect(Collectors.toList());

        saveAchievementsToDB(pairs
                .stream()
                .map(AchievementsPair::getDbModel)
                .collect(Collectors.toList()));

        assignAchievementsToRoutes(pairs);
    }

    private void saveAchievementsToDB(List<DBAchievement> achievements) {
        achievementRepository.saveAll(achievements);
    }

    private void assignAchievementsToRoutes(List<AchievementsPair> pairs) {
        pairs.forEach(pair -> {
            var route = routeRepository.findById(pair.dto.routeId);
            route.ifPresent(unwrappedRoute -> unwrappedRoute.setAchievement(pair.getDbModel()));
        });
    }

    private static class AchievementsPair {
        private final AchievementUploadDto dto;
        private final DBAchievement dbModel;

        AchievementsPair(AchievementUploadDto dto,  DBAchievement dbModel) {
            this.dto = dto;
            this.dbModel = dbModel;
        }

        public AchievementUploadDto getDto() {
            return dto;
        }

        public DBAchievement getDbModel() {
            return dbModel;
        }
    }
}

class AchievementUploadDtoMapper {
    private AchievementUploadDtoMapper() {}

    static DBAchievement map(AchievementUploadDto dto) {
        return new DBAchievement(
                Objects.nonNull(dto.id) ? dto.id : UUID.randomUUID(),
                dto.name,
                dto.description,
                null,
                null);
    }
}