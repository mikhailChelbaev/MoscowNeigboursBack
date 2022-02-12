package com.moscow.neighbours.backend.controllers.routes.service.interfaces;

import com.moscow.neighbours.backend.controllers.routes.dto.RouteDto;

import java.util.List;
import java.util.UUID;

public interface IRouteService {
    List<RouteDto> getAllRoutes(boolean withPaidRoutes);
    List<RouteDto> getRoutesForUser(String userId);

    public void purchaseProduct(String userId, UUID routeId);
}
