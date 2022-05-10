package com.moscow.neighbours.backend.controllers.routes.dto;

import com.moscow.neighbours.backend.db.model.DBAchievement;
import lombok.Data;

@Data
public class RouteAchievementDto {
    String name;
    String date;
    String imageUrl;

    public RouteAchievementDto (DBAchievement dbModel) {
        name = dbModel.getName();
        date = dbModel.getDate();
        imageUrl = dbModel.getImageUrl();
    }
}