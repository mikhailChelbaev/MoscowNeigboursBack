package com.moscow.neighbours.backend.controllers.upload.dto;

import java.util.UUID;

public class AchievementUploadDto {
    public UUID id;
    public String name;
    public String completedDescription;
    public String uncompletedDescription;
    public UUID routeId;
}
