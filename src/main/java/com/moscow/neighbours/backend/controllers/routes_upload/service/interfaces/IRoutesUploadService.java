package com.moscow.neighbours.backend.controllers.routes_upload.service.interfaces;

import com.moscow.neighbours.backend.controllers.routes.dto.RouteDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IRoutesUploadService {

    void saveRoutes(List<RouteDto> routes);

    String updatePersonAvatar(UUID personId, MultipartFile file);

    String updateRouteCover(UUID routeId, MultipartFile file);

}
