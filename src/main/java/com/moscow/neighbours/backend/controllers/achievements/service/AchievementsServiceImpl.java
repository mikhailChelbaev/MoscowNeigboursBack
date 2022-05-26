package com.moscow.neighbours.backend.controllers.achievements.service;

import com.moscow.neighbours.backend.controllers.achievements.dto.AchievementDto;
import com.moscow.neighbours.backend.controllers.achievements.dto.AchievementSectionDto;
import com.moscow.neighbours.backend.controllers.achievements.dto.CompletedAchievementDto;
import com.moscow.neighbours.backend.db.datasource.AchievementRepository;
import com.moscow.neighbours.backend.db.datasource.CompletedAchievementRepository;
import com.moscow.neighbours.backend.db.datasource.UserRepository;
import com.moscow.neighbours.backend.db.model.achievements.DBAchievement;
import com.moscow.neighbours.backend.db.model.achievements.DBCompletedAchievement;
import com.moscow.neighbours.backend.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AchievementsServiceImpl implements IAchievementsStore, IAchievementsLoader {
    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;
    private final CompletedAchievementRepository completedAchievementRepository;

    public AchievementsServiceImpl(
            AchievementRepository achievementRepository,
            UserRepository userRepository,
            CompletedAchievementRepository completedAchievementRepository) {
        this.achievementRepository = achievementRepository;
        this.userRepository = userRepository;
        this.completedAchievementRepository = completedAchievementRepository;
    }

    // MARK: - IAchievementsStore

    @Override
    public void saveAchievement(String email, CompletedAchievementDto dto) {
        var user = userRepository.findByUserId(email).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        user.getCompletedAchievements()
                .stream()
                .filter(achievement -> achievement.getAchievementId().equals(dto.achievementId))
                .findFirst()
                .ifPresentOrElse(completedAchievement -> {
                    completedAchievement.setDate(dto.date);
                    completedAchievementRepository.saveAndFlush(completedAchievement);
                }, () -> {
                    var completedAchievement = new DBCompletedAchievement(
                            UUID.randomUUID(),
                            user,
                            dto.achievementId,
                            dto.date);
                    completedAchievementRepository.saveAndFlush(completedAchievement);

                    user.getCompletedAchievements().add(completedAchievement);
                    userRepository.saveAndFlush(user);
                });
    }

    // MARK: - IAchievementsLoader

    @Override
    public List<AchievementSectionDto> getAchievements(String email) {
        List<AchievementSectionDto> sections = new ArrayList<>();

        var allAchievements = getAllAchievements(email);
        var completedAchievements = getCompletedAchievements(email);

        if (!completedAchievements.isEmpty()) {
            sections.add(new AchievementSectionDto("Полученные", completedAchievements));
        }
        sections.add(new AchievementSectionDto("Доступные", allAchievements));

        return sections;
    }

    private List<AchievementDto> getAllAchievements(String userEmail) {
        var user = userRepository.findByUserId(userEmail).orElseThrow(() -> {
                throw new UserNotFoundException();
        });

        var allDbAchievements = achievementRepository.findAll();
        var allAchievements = allDbAchievements
                .stream()
                .map(achievement -> {
                    var completedAchievement = user
                            .getCompletedAchievements()
                            .stream()
                            .filter(x -> x.getAchievementId().equals(achievement.getId()))
                            .findFirst();
                    var date = completedAchievement.map(DBCompletedAchievement::getDate)
                            .orElse(null);
                    return AchievementMapper.map(achievement, date);
                })
                .collect(Collectors.toList());
        return allAchievements;
    }

    private List<AchievementDto> getCompletedAchievements(String userEmail) {
        var user = userRepository.findByUserId(userEmail);

        return user.map(unwrappedUser -> unwrappedUser.getCompletedAchievements()
                .stream()
                .map(data -> {
            var achievement = achievementRepository.findById(data.getAchievementId());
            return achievement.map(dbAchievement -> AchievementMapper.map(dbAchievement, data.getDate()))
                    .orElse(null);
        }).filter(Objects::nonNull)
                .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }
}

class AchievementMapper {
    private AchievementMapper() {
    }

    private static String getImage(DBAchievement dbModel, Date date) {
        return Objects.nonNull(date) ? dbModel.getCompletedImageUrl() : dbModel.getUncompletedImageUrl();
    }

    private static String getDescription(DBAchievement dbModel, Date date) {
        return Objects.nonNull(date) ? dbModel.getCompletedDescription() : dbModel.getUncompletedDescription();
    }

    static AchievementDto map(DBAchievement dbModel, Date date) {
        return new AchievementDto(dbModel.getId(), dbModel.getName(), getDescription(dbModel, date), getImage(dbModel, date), date);
    }
}