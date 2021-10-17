package com.moscow.neighbours.backend.controllers.routes.service.interfaces;

import com.moscow.neighbours.backend.controllers.routes.dto.RouteDto;

import java.util.List;

public interface IRouteService {

    List<RouteDto> getRoutes();

}
