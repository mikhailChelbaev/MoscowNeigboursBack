package com.moscow.neighbours.backend.controllers.routes.dto;

import com.moscow.neighbours.backend.db.model.achievements.DBAchievement;
import lombok.Data;

@Data
public class RouteAchievementDto {
    String name;
    String imageUrl;

    public RouteAchievementDto (DBAchievement dbModel) {
        name = dbModel.getName();
        imageUrl = dbModel.getCompletedImageUrl();
    }
}