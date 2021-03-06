package com.moscow.neighbours.backend.controllers.routes.service.impl;

import com.moscow.neighbours.backend.controllers.routes.dto.RouteAchievementDto;
import com.moscow.neighbours.backend.controllers.routes.dto.RouteDto;
import com.moscow.neighbours.backend.controllers.routes.service.exceptions.FetchRoutesException;
import com.moscow.neighbours.backend.controllers.routes.service.exceptions.RoutePurchaseException;
import com.moscow.neighbours.backend.controllers.routes.service.interfaces.IPurchaseService;
import com.moscow.neighbours.backend.controllers.routes.service.interfaces.IRouteService;
import com.moscow.neighbours.backend.db.datasource.RouteRepository;
import com.moscow.neighbours.backend.db.datasource.UserRepository;
import com.moscow.neighbours.backend.db.model.achievements.DBCompletedAchievement;
import com.moscow.neighbours.backend.db.model.route.DBRoute;
import com.moscow.neighbours.backend.db.model.user.DBUser;
import com.moscow.neighbours.backend.models.RoutePurchaseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoutesServiceImpl implements IRouteService {
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoutesServiceImpl(RouteRepository routeRepository,
                             UserRepository userRepository) {
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
    }

    @Override
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

        var purchasedRouteIds = fetPurchasedRouteIds(user);
        var receivedAchievements = getReceivedAchievements(user);

        return dbRoutes.stream()
                .map(dbRoute -> createRouteDto(dbRoute, purchasedRouteIds, receivedAchievements))
                .sorted(Comparator.comparing(x -> x.position))
                .collect(Collectors.toList());
    }

    // MARK - Helpers

    private List<UUID> fetPurchasedRouteIds(DBUser user) {
        return user.getPurchasedRoutes()
                .stream()
                .map(DBRoute::getId)
                .collect(Collectors.toList());
    }

    private List<UUID> getReceivedAchievements(DBUser user) {
        return user.getCompletedAchievements()
                .stream()
                .map(DBCompletedAchievement::getAchievementId)
                .collect(Collectors.toList());
    }

    private RouteDto createRouteDto(
            DBRoute dbRoute,
            List<UUID> purchasedRouteIds,
            List<UUID> receivedAchievements) {
        var route = new RouteDto(dbRoute);
        if (purchasedRouteIds.contains(route.getId())) {
            route.purchase.status = RoutePurchaseStatus.PURCHASED.name().toLowerCase();
        }
        if (dbRoute.getAchievement() != null && !receivedAchievements.contains(dbRoute.getAchievement().getId())) {
            route.achievement = new RouteAchievementDto(dbRoute.getAchievement());
        }
        return route;
    }
}
