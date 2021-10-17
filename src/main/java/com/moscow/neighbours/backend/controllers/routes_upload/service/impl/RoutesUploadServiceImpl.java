package com.moscow.neighbours.backend.controllers.routes_upload.service.impl;

import com.moscow.neighbours.backend.controllers.routes.dto.RouteDto;
import com.moscow.neighbours.backend.controllers.routes.service.interfaces.IRouteService;
import com.moscow.neighbours.backend.controllers.routes_upload.service.interfaces.IRoutesUploadService;
import com.moscow.neighbours.backend.db.datasource.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoutesUploadServiceImpl implements IRoutesUploadService, Serializable {

    private final RouteRepository routeRepository;

    @Autowired
    public RoutesUploadServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public void saveRoutes(List<RouteDto> routes) {
        routeRepository.saveAll(
                routes.stream()
                        .map(RouteDto::toDBModel)
                        .collect(Collectors.toList())
        );
    }

}