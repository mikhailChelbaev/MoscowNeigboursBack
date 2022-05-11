package com.moscow.neighbours.backend.controllers.upload.service.impl;

import com.moscow.neighbours.backend.controllers.images.service.interfaces.IImageUploadService;
import com.moscow.neighbours.backend.controllers.upload.exceptions.RoutesUploadControllerException;
import com.moscow.neighbours.backend.controllers.upload.service.interfaces.IUploadRouteImagesService;
import com.moscow.neighbours.backend.db.datasource.AchievementRepository;
import com.moscow.neighbours.backend.db.datasource.PersonRepository;
import com.moscow.neighbours.backend.db.datasource.RouteRepository;
import com.moscow.neighbours.backend.db.model.achievements.DBAchievement;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
public class UploadRouteImagesServiceImpl implements IUploadRouteImagesService {

    private final RouteRepository routeRepository;
    private final PersonRepository personRepository;
    private final AchievementRepository achievementRepository;
    private final IImageUploadService imageUploadService;

    public UploadRouteImagesServiceImpl(
            RouteRepository routeRepository,
            PersonRepository personRepository,
            AchievementRepository achievementRepository,
            IImageUploadService imageUploadService
    ) {
        this.routeRepository = routeRepository;
        this.personRepository = personRepository;
        this.achievementRepository = achievementRepository;
        this.imageUploadService = imageUploadService;
    }

    @Override
    public String updatePersonAvatar(UUID personId, MultipartFile file) {
        var person = personRepository.findById(personId).orElseThrow(() -> {
            throw new RoutesUploadControllerException("There is no person with given id");
        });

        deleteImage(person.getAvatarUrl());
        var fileDownloadUri = getFileDownloadUri(file);
        person.setAvatarUrl(fileDownloadUri);
        personRepository.save(person);

        return fileDownloadUri;
    }

    @Override
    public String updateRouteCover(UUID routeId, MultipartFile file) {
        var route = routeRepository.findById(routeId)
                .orElseThrow(() -> {
            throw new RoutesUploadControllerException("There is no route with given id");
        });

        deleteImage(route.getCoverUrl());
        var fileDownloadUri = getFileDownloadUri(file);
        route.setCoverUrl(fileDownloadUri);
        routeRepository.save(route);

        return fileDownloadUri;
    }

    @Override
    public String updateCompletedAchievementImage(UUID achievementId, MultipartFile file) {
        var achievement = achievementRepository.findById(achievementId).orElseThrow(() -> {
            throw new RoutesUploadControllerException("There is no achievement with given id");
        });

        deleteImage(achievement.getCompletedImageUrl());
        var fileDownloadUri = getFileDownloadUri(file);
        achievement.setCompletedImageUrl(fileDownloadUri);
        achievementRepository.save(achievement);

        return fileDownloadUri;
    }

    @Override
    public String updateUncompletedAchievementImage(UUID achievementId, MultipartFile file) {
        var achievement = achievementRepository.findById(achievementId).orElseThrow(() -> {
            throw new RoutesUploadControllerException("There is no achievement with given id");
        });

        deleteImage(achievement.getUncompletedImageUrl());
        var fileDownloadUri = getFileDownloadUri(file);
        achievement.setUncompletedImageUrl(fileDownloadUri);
        achievementRepository.save(achievement);

        return fileDownloadUri;
    }

    // MARK: - Helpers

    private String getFileDownloadUri(MultipartFile file) {
        try {
            Path path = imageUploadService.saveUploadedFile(file, UUID.randomUUID().toString());
            return imageUploadService.getUploadUri(path);

        } catch (Exception ex) {
            throw new RoutesUploadControllerException(ex.getMessage());
        }
    }

    private String parseFileName(String path) throws RoutesUploadControllerException {
        var parts = path.split("/");
        var length = parts.length;
        if (length == 0) {
            throw new RoutesUploadControllerException("Wrong user avatar url");
        }
        return parts[length - 1];
    }

    private void deleteImage(String imageUlr) {
        if (imageUlr == null) { return; }

        var filename = parseFileName(imageUlr);
        imageUploadService.deleteFile(filename);
    }
}
