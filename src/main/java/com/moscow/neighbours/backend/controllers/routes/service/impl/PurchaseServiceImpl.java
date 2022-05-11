package com.moscow.neighbours.backend.controllers.routes.service.impl;

import com.moscow.neighbours.backend.controllers.routes.service.exceptions.RoutePurchaseException;
import com.moscow.neighbours.backend.controllers.routes.service.interfaces.IPurchaseService;
import com.moscow.neighbours.backend.db.datasource.RouteRepository;
import com.moscow.neighbours.backend.db.datasource.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PurchaseServiceImpl implements IPurchaseService {
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;

    @Autowired
    public PurchaseServiceImpl(RouteRepository routeRepository, UserRepository userRepository) {
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
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
