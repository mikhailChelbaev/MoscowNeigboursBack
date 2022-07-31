package com.moscow.neighbours.backend.controllers.upload.service.impl;

import com.moscow.neighbours.backend.controllers.routes.dto.RouteDto;
import com.moscow.neighbours.backend.controllers.upload.exceptions.RoutesUploadControllerException;
import com.moscow.neighbours.backend.controllers.upload.service.interfaces.IRoutesUploadService;
import com.moscow.neighbours.backend.db.datasource.RouteRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoutesUploadServiceImpl implements IRoutesUploadService, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(RoutesUploadServiceImpl.class);
    private final RouteRepository routeRepository;

    @Autowired
    public RoutesUploadServiceImpl(
            RouteRepository routeRepository
    ) {
        this.routeRepository = routeRepository;
    }

    public void saveRoutes(List<RouteDto> routes) {
        try {
            var dbRoutes = routes.stream()
                    .map(RouteDto::toDBModel)
                    .collect(Collectors.toList());
            routeRepository.saveAllAndFlush(dbRoutes);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new RoutesUploadControllerException("Failed to save routes to DB");
        }
    }
}