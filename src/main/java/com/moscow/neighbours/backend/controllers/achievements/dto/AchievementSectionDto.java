package com.moscow.neighbours.backend.controllers.achievements.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AchievementSectionDto {
    String title;
    List<AchievementDto> achievements;
}
