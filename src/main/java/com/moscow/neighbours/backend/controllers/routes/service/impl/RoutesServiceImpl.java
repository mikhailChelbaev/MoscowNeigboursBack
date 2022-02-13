package com.moscow.neighbours.backend.controllers.routes.service.impl;

import com.moscow.neighbours.backend.controllers.routes.dto.RouteDto;
import com.moscow.neighbours.backend.controllers.routes.dto.RoutePurchaseDto;
import com.moscow.neighbours.backend.controllers.routes.service.exceptions.FetchRoutesException;
import com.moscow.neighbours.backend.controllers.routes.service.exceptions.RoutePurchaseException;
import com.moscow.neighbours.backend.controllers.routes.service.interfaces.IRouteService;
import com.moscow.neighbours.backend.db.datasource.RouteRepository;
import com.moscow.neighbours.backend.db.datasource.UserRepository;
import com.moscow.neighbours.backend.db.model.DBRoute;
import com.moscow.neighbours.backend.exceptions.UserNotFoundException;
import com.moscow.neighbours.backend.models.RoutePurchaseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoutesServiceImpl implements IRouteService, Serializable {

    private final RouteRepository routeRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoutesServiceImpl(RouteRepository routeRepository,
                             UserRepository userRepository) {
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
    }

    public List<RouteDto> getAllRoutes(boolean withPaidRoutes) {
        var dbRoutes = routeRepository.findAll();
        return dbRoutes.stream()
                .filter(x -> withPaidRoutes || !x.getPurchase().getStatus().name().equals("BUY"))
                .map(RouteDto::new)
                .sorted(Comparator.comparing(x -> x.position))
                .collect(Collectors.toList());
    }

    @Override
    public List<RouteDto> getRoutesForUser(String userId) {
        var dbRoutes = routeRepository.findAll();
        var user = userRepository.findByUserId(userId).orElseThrow(
                () -> new FetchRoutesException("User not found")
        );
        var purchasedRouteIds = user.getPurchasedRoutes().stream().map(
                DBRoute::getId
        ).collect(Collectors.toList());

        return dbRoutes.stream()
                .map(dbRoute -> {
                    var route = new RouteDto(dbRoute);
                    if (purchasedRouteIds.contains(route.getId())) {
                        route.purchase.status = RoutePurchaseStatus.PURCHASED.name().toLowerCase();
                    }
                    return route;
                })
                .sorted(Comparator.comparing(x -> x.position))
                .collect(Collectors.toList());
    }

    @Override
    public void purchaseProduct(String userId, UUID routeId) {
        var user = userRepository.findByUserId(userId).orElseThrow(
                () -> new RoutePurchaseException("User not found")
        );
        var route = routeRepository.findById(routeId).orElseThrow(
                () -> new RoutePurchaseException("Route not found")
        );
        user.getPurchasedRoutes().add(route);
        userRepository.saveAndFlush(user);
    }
}
