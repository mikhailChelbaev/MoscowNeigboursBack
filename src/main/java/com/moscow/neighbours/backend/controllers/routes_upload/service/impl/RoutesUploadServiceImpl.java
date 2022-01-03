package com.moscow.neighbours.backend.controllers.routes_upload.service.impl;

import com.moscow.neighbours.backend.controllers.images.service.interfaces.IImageUploadService;
import com.moscow.neighbours.backend.controllers.routes.dto.RouteDto;
import com.moscow.neighbours.backend.controllers.routes_upload.exceptions.RoutesUploadControllerException;
import com.moscow.neighbours.backend.controllers.routes_upload.service.interfaces.IRoutesUploadService;
import com.moscow.neighbours.backend.db.datasource.PersonRepository;
import com.moscow.neighbours.backend.db.datasource.RouteRepository;
import com.moscow.neighbours.backend.db.model.DBRoute;
import com.moscow.neighbours.backend.db.model.entities.DBPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoutesUploadServiceImpl implements IRoutesUploadService, Serializable {

    private final RouteRepository routeRepository;

    private final PersonRepository personRepository;

    private final IImageUploadService imageUploadService;

    @Autowired
    public RoutesUploadServiceImpl(
            RouteRepository routeRepository,
            PersonRepository personRepository,
            IImageUploadService imageUploadService) {
        this.routeRepository = routeRepository;
        this.personRepository = personRepository;
        this.imageUploadService = imageUploadService;
    }

    public void saveRoutes(List<RouteDto> routes) {
        try {
            var dbRoutes = routes.stream()
                    .map(RouteDto::toDBModel)
                    .collect(Collectors.toList());
            routeRepository.saveAllAndFlush(dbRoutes);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    public String updatePersonAvatar(UUID personId, MultipartFile file) {
        Optional<DBPerson> person = personRepository.findById(personId);
        try {
            if (person.isEmpty()) {
                throw new RoutesUploadControllerException("There is no person with given id");
            }
            var unwrappedPerson = person.get();
            if (unwrappedPerson.getAvatarUrl() != null) {
                var avatarUrl = unwrappedPerson.getAvatarUrl();
                var filename = parseFileName(avatarUrl);
                imageUploadService.deleteFile(filename);
            }
            Path path = imageUploadService.saveUploadedFile(file, UUID.randomUUID().toString());
            var fileDownloadUri = imageUploadService.getUploadUri(path);

            unwrappedPerson.setAvatarUrl(fileDownloadUri);
            personRepository.save(unwrappedPerson);

            return fileDownloadUri;
        } catch (Exception ex) {
            throw new RoutesUploadControllerException(ex.getMessage());
        }
    }

    @Override
    public String updateRouteCover(UUID routeId, MultipartFile file) {
        Optional<DBRoute> route = routeRepository.findById(routeId);
        try {
            if (route.isEmpty()) {
                throw new RoutesUploadControllerException("There is no route with given id");
            }
            var unwrappedRoute = route.get();
            if (unwrappedRoute.getCoverUrl() != null) {
                var coverUrl = unwrappedRoute.getCoverUrl();
                var filename = parseFileName(coverUrl);
                imageUploadService.deleteFile(filename);
            }
            Path path = imageUploadService.saveUploadedFile(file, UUID.randomUUID().toString());
            var fileDownloadUri = imageUploadService.getUploadUri(path);

            unwrappedRoute.setCoverUrl(fileDownloadUri);
            routeRepository.save(unwrappedRoute);

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

    private static final Logger logger = LoggerFactory.getLogger(RoutesUploadServiceImpl.class);

}