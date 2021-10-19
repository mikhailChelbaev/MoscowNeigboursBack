package com.moscow.neighbours.backend.controllers.routes.service.impl;

import com.moscow.neighbours.backend.controllers.routes.dto.RouteDto;
import com.moscow.neighbours.backend.controllers.routes.service.interfaces.IRouteService;
import com.moscow.neighbours.backend.controllers.routes_upload.RoutesUploadController;
import com.moscow.neighbours.backend.db.datasource.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoutesServiceImpl implements IRouteService, Serializable {

    private final RouteRepository routeRepository;

    @Autowired
    public RoutesServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<RouteDto> getRoutes() {
        return routeRepository.findAll().stream()
                .map(RouteDto::new)
                .collect(Collectors.toList());
    }

}
