package com.moscow.neighbours.backend.controllers.routes;

import com.moscow.neighbours.backend.controllers.routes.dto.RouteDto;
import com.moscow.neighbours.backend.controllers.routes.service.interfaces.IRouteService;
import com.moscow.neighbours.backend.dto.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    public List<RouteDto> getRoutes(@RequestHeader Map<String, String> headers) {
        var withPaidRoutes = false;

        var version = headers.get("version");
        try {
            var versionValue = Float.parseFloat(version);
            if (versionValue > 1.2) {
                withPaidRoutes = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Failed to get app version");
        }

        return routeService.getRoutes(withPaidRoutes);
    }

}
