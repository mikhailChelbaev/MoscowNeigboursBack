package com.moscow.neighbours.backend.controllers.routes.dto;

import com.moscow.neighbours.backend.db.model.achievements.DBAchievement;
import lombok.Data;

import java.util.UUID;

@Data
public class RouteAchievementDto {
    UUID id;
    String name;
    String imageUrl;

    public RouteAchievementDto (DBAchievement dbModel) {
        id = dbModel.getId();
        name = dbModel.getName();
        imageUrl = dbModel.getCompletedImageUrl();
    }
}