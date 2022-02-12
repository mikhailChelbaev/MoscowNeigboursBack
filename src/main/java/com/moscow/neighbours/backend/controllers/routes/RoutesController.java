package com.moscow.neighbours.backend.controllers.routes;

import com.moscow.neighbours.backend.controllers.routes.dto.RouteDto;
import com.moscow.neighbours.backend.controllers.routes.service.interfaces.IRouteService;
import com.moscow.neighbours.backend.dto.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping(path="/api/v1/routes")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@Slf4j
public class RoutesController {

    private final IRouteService routeService;

    @Autowired
    public RoutesController(
            IRouteService routeService
    ) {
        this.routeService = routeService;
    }

    @GetMapping()
    public List<RouteDto> getRoutes(@RequestHeader Map<String, String> headers,
                                    Principal user) {
        var version = headers.get("version");
        float versionValue = 1;
        try {
            versionValue = Float.parseFloat(version);
        } catch (Exception ex) {
            log.error("Failed to get app version");
        }

        if (user != null) {
            return routeService.getRoutesForUser(user.getName());
        } else {
            return routeService.getAllRoutes(versionValue > 1.2);
        }
    }

    @PostMapping("purchase/{id}")
    public ResponseEntity<?> purchaseProduct(@PathVariable("id") UUID id,
                                             Principal user) {
        if (user == null) {
            return ResponseEntity.badRequest()
                    .body(MessageResponse.of("User not authorized"));
        }
        routeService.purchaseProduct(user.getName(), id);
        return ResponseEntity.ok(MessageResponse.of("Product purchased successfully"));
    }

}
