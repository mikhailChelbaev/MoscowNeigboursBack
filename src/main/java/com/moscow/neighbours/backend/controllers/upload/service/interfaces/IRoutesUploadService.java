package com.moscow.neighbours.backend.controllers.upload.service.interfaces;

import com.moscow.neighbours.backend.controllers.routes.dto.RouteDto;

import java.util.List;

public interface IRoutesUploadService {
    void saveRoutes(List<RouteDto> routes);
}
