package com.moscow.neighbours.backend.controllers.routes.dto;

import com.moscow.neighbours.backend.db.model.achievements.DBAchievement;
import lombok.Data;

@Data
public class RouteAchievementDto {
    String name;
    String date;
    String imageUrl;

    public RouteAchievementDto (DBAchievement dbModel, String receiveDate) {
        name = dbModel.getName();
        date = receiveDate;
        imageUrl = dbModel.getImageUrl();
    }
}