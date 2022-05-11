package com.moscow.neighbours.backend.controllers.achievements.service;

import com.moscow.neighbours.backend.controllers.achievements.dto.AchievementDto;
import com.moscow.neighbours.backend.controllers.achievements.dto.AchievementSectionDto;
import com.moscow.neighbours.backend.db.datasource.AchievementRepository;
import com.moscow.neighbours.backend.db.datasource.UserRepository;
import com.moscow.neighbours.backend.db.model.DBAchievement;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AchievementsServiceImpl implements IAchievementsService {
    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;

    public AchievementsServiceImpl(
            AchievementRepository achievementRepository,
            UserRepository userRepository
    ) {
        this.achievementRepository = achievementRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<AchievementSectionDto> getAchievements(String email) {
        List<AchievementSectionDto> sections = new ArrayList<>();

        var allAchievements = getAllAchievements();
        var completedAchievements = getCompletedAchievements(email);

        if (!completedAchievements.isEmpty()) {
            sections.add(new AchievementSectionDto("Полученные", completedAchievements));
        }
        sections.add(new AchievementSectionDto("Доступные", allAchievements));

        return sections;
    }

    // MARK: - Helpers

    private List<AchievementDto> getAllAchievements() {
        var allDbAchievements = achievementRepository.findAll();
        var allAchievements = allDbAchievements
                .stream()
                .map(achievement -> AchievementMapper.map(achievement, null))
                .collect(Collectors.toList());
        return allAchievements;
    }

    private List<AchievementDto> getCompletedAchievements(String userEmail) {
        var user = userRepository.findByUserId(userEmail);

        return user.map(unwrappedUser -> {
                    return unwrappedUser
                            .getCompletedAchievements()
                            .stream()
                            .map(data -> {
                                var achievement = achievementRepository.findById(data.getAchievementId());
                                return achievement
                                        .map(dbAchievement -> AchievementMapper.map(dbAchievement, data.getDate()))
                                        .orElse(null);
                            })
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                })
                .orElse(new ArrayList<>());
    }
}

class AchievementMapper {
    private AchievementMapper() {
    }

    static AchievementDto map(DBAchievement dbModel, Date date) {
        return new AchievementDto(
                dbModel.getName(),
                dbModel.getDescription(),
                date,
                dbModel.getImageUrl());
    }
}