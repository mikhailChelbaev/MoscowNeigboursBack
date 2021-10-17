package com.moscow.neighbours.backend.controllers.routes_upload;

import com.moscow.neighbours.backend.controllers.routes.dto.RouteDto;
import com.moscow.neighbours.backend.controllers.routes.service.interfaces.IRouteService;
import com.moscow.neighbours.backend.controllers.routes_upload.service.interfaces.IRoutesUploadService;
import com.moscow.neighbours.backend.dto.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/routes/hidden/upload")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
public class RoutesUploadController {

    private final IRoutesUploadService uploadService;

    @Autowired
    public RoutesUploadController(
            IRoutesUploadService uploadService
    ) {
        this.uploadService = uploadService;
    }

    @PostMapping()
    public ResponseEntity<?> uploadRoutes(@RequestBody List<RouteDto> routesUploadDtoList) {
        uploadService.saveRoutes(routesUploadDtoList);
        return ResponseEntity.ok(MessageResponse.of("Routes uploaded successfully"));
    }

}
