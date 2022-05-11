package com.moscow.neighbours.backend.controllers.achievements.service;

import com.moscow.neighbours.backend.controllers.achievements.dto.AchievementDto;
import com.moscow.neighbours.backend.controllers.achievements.dto.AchievementSectionDto;
import com.moscow.neighbours.backend.controllers.achievements.dto.CompletedAchievementDto;
import com.moscow.neighbours.backend.db.datasource.AchievementRepository;
import com.moscow.neighbours.backend.db.datasource.UserRepository;
import com.moscow.neighbours.backend.db.model.achievements.DBAchievement;
import com.moscow.neighbours.backend.db.model.achievements.DBCompletedAchievement;
import com.moscow.neighbours.backend.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AchievementsServiceImpl implements IAchievementsStore, IAchievementsLoader {
    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;

    public AchievementsServiceImpl(
            AchievementRepository achievementRepository,
            UserRepository userRepository
    ) {
        this.achievementRepository = achievementRepository;
        this.userRepository = userRepository;
    }

    // MARK: - IAchievementsStore

    @Override
    public void saveAchievement(String email, CompletedAchievementDto dto) {
        var user = userRepository.findByUserId(email);
        user.ifPresentOrElse(unwrappedUser -> unwrappedUser
                .getCompletedAchievements()
                .add(new DBCompletedAchievement(dto.achievementId, dto.date)),
                () -> {
            throw new UserNotFoundException();
        });
    }

    // MARK: - IAchievementsLoader

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

        return user.map(unwrappedUser -> unwrappedUser
                .getCompletedAchievements()
                .stream()
                .map(data -> {
                    var achievement = achievementRepository.findById(data.getAchievementId());
                    return achievement
                            .map(dbAchievement -> AchievementMapper.map(dbAchievement, data.getDate()))
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }
}

class AchievementMapper {
    private AchievementMapper() {}

    private static String getImage(DBAchievement dbModel, Date date) {
        return Objects.nonNull(date) ? dbModel.getCompletedImageUrl() : dbModel.getUncompletedImageUrl();
    }

    private static String getDescription(DBAchievement dbModel, Date date) {
        return Objects.nonNull(date) ? dbModel.getCompletedDescription() : dbModel.getUncompletedDescription();
    }

    static AchievementDto map(DBAchievement dbModel, Date date) {
        return new AchievementDto(
                dbModel.getId(),
                dbModel.getName(),
                getDescription(dbModel, date),
                date,
                getImage(dbModel, date));
    }
}