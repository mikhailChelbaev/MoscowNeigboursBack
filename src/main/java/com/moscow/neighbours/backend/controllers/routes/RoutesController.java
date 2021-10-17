package com.moscow.neighbours.backend.controllers.routes;

import com.moscow.neighbours.backend.controllers.routes.dto.RouteDto;
import com.moscow.neighbours.backend.controllers.routes.service.interfaces.IRouteService;
import com.moscow.neighbours.backend.dto.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/routes")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
public class RoutesController {

    private final IRouteService routeService;

    @Autowired
    public RoutesController(
            IRouteService routeService
    ) {
        this.routeService = routeService;
    }

    @GetMapping()
    public List<RouteDto> getRoutes() {
        return routeService.getRoutes();
    }

}
