package com.moscow.neighbours.backend.controllers.routes.service.interfaces;

import java.util.UUID;

public interface IPurchaseService {
    void purchaseProduct(String userId, UUID routeId);
}
