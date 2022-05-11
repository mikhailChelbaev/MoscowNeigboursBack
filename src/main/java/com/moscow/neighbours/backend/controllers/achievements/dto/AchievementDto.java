package com.moscow.neighbours.backend.controllers.achievements.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AchievementDto {
    String name;
    String description;
    Date date;
    String imageUrl;
}
