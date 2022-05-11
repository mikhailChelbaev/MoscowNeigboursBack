package com.moscow.neighbours.backend.controllers.upload.service.impl;

import com.moscow.neighbours.backend.controllers.images.service.interfaces.IImageUploadService;
import com.moscow.neighbours.backend.controllers.upload.exceptions.RoutesUploadControllerException;
import com.moscow.neighbours.backend.controllers.upload.service.interfaces.IUploadRouteImagesService;
import com.moscow.neighbours.backend.db.ImagePresentable;
import com.moscow.neighbours.backend.db.datasource.AchievementRepository;
import com.moscow.neighbours.backend.db.datasource.PersonRepository;
import com.moscow.neighbours.backend.db.datasource.RouteRepository;
import com.moscow.neighbours.backend.db.model.achievements.DBAchievement;
import com.moscow.neighbours.backend.db.model.route.DBRoute;
import com.moscow.neighbours.backend.db.model.route.DBPerson;
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
        Optional<DBPerson> person = personRepository.findById(personId);

        if (person.isEmpty()) {
            throw new RoutesUploadControllerException("There is no person with given id");
        }

        var unwrappedPerson = person.get();
        var fileDownloadUri = updateImage(unwrappedPerson, file);
        personRepository.save(unwrappedPerson);
        return fileDownloadUri;
    }

    @Override
    public String updateRouteCover(UUID routeId, MultipartFile file) {
        Optional<DBRoute> route = routeRepository.findById(routeId);

        if (route.isEmpty()) {
            throw new RoutesUploadControllerException("There is no route with given id");
        }

        var unwrappedRoute = route.get();
        var fileDownloadUri = updateImage(unwrappedRoute, file);
        routeRepository.save(unwrappedRoute);
        return fileDownloadUri;
    }

    @Override
    public String updateAchievementImage(UUID achievementId, MultipartFile file) {
        Optional<DBAchievement> achievement = achievementRepository.findById(achievementId);

        if (achievement.isEmpty()) {
            throw new RoutesUploadControllerException("There is no achievement with given id");
        }

        var unwrappedAchievement = achievement.get();
        var fileDownloadUri = updateImage(unwrappedAchievement, file);
        achievementRepository.save(unwrappedAchievement);
        return fileDownloadUri;
    }

    // MARK: - Helpers

    private <T extends ImagePresentable> String updateImage(T entity, MultipartFile file) {
        try {
            deleteImage(entity.getImageUrl());
            Path path = imageUploadService.saveUploadedFile(file, UUID.randomUUID().toString());
            var fileDownloadUri = imageUploadService.getUploadUri(path);

            entity.setImageUrl(fileDownloadUri);

            return fileDownloadUri;
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
