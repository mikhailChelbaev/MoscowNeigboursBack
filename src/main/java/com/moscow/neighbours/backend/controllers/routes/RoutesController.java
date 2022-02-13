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
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
        List<Integer> versionValue = Arrays.asList(1, 0, 0);

        if (version != null) {
            try {
                versionValue = Arrays.stream(version.split("\\."))
                        .map(Integer::valueOf)
                        .collect(Collectors.toList());
            } catch (Exception ex) {
                log.error("Failed to get app version");
            }
        }

        if (compareVersions(versionValue, Arrays.asList(1, 2, 0)) <= 0) {
            return routeService.getAllRoutes(false);
        }

        if (user != null) {
            return routeService.getRoutesForUser(user.getName());
        } else {
            return routeService.getAllRoutes(true);
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

    // MARK: - Helpers

    private int compareVersions(List<Integer> lhs, List<Integer> rhs) {
        while (lhs.size() < 3) {
            lhs.add(0);
        }
        while (rhs.size() < 3) {
            rhs.add(0);
        }

        if (lhs.get(0) < rhs.get(0)) {
            return -1;
        } else if (lhs.get(0) > rhs.get(0)) {
            return 1;
        } else {
            if (lhs.get(1) < rhs.get(1)) {
                return -1;
            } else if (lhs.get(1) > rhs.get(1)) {
                return 1;
            } else {
                return lhs.get(2).compareTo(rhs.get(2));
            }
        }
    }

}
