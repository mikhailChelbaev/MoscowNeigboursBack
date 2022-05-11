package com.moscow.neighbours.backend.controllers.upload.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IUploadRouteImagesService {
    String updatePersonAvatar(UUID personId, MultipartFile file);
    String updateRouteCover(UUID routeId, MultipartFile file);
    String updateCompletedAchievementImage(UUID achievementId, MultipartFile file);
    String updateUncompletedAchievementImage(UUID achievementId, MultipartFile file);
}
