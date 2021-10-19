package com.moscow.neighbours.backend.controllers.routes_upload;

import com.moscow.neighbours.backend.controllers.routes.dto.RouteDto;
import com.moscow.neighbours.backend.controllers.routes_upload.dto.ImageUploadResponseDto;
import com.moscow.neighbours.backend.controllers.routes_upload.service.interfaces.IRoutesUploadService;
import com.moscow.neighbours.backend.dto.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import javax.validation.Valid;

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
        logger.info("POST: /api/routes/hidden/upload/");
        uploadService.saveRoutes(routesUploadDtoList);
        return ResponseEntity.ok(MessageResponse.of("Routes uploaded successfully"));
    }

    @RequestMapping(value = "/person/avatar", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<?> uploadPersonAvatar(
            @RequestParam("person_id") UUID personId,
            @RequestPart("file") @Valid MultipartFile file
    ) {
        logger.info("POST: /api/routes/hidden/upload/person/avatar{}", personId);

        return ResponseEntity.ok(
                ImageUploadResponseDto.of(
                        uploadService.updatePersonAvatar(personId, file)
                )
        );
    }

    @RequestMapping(value = "/route/cover", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<?> uploadRouteCover(
            @RequestParam("route_id") UUID routeId,
            @RequestPart("file") @Valid MultipartFile file
    ) {
        logger.info("POST: /api/routes/hidden/upload/route/cover{}", routeId);

        return ResponseEntity.ok(
                ImageUploadResponseDto.of(
                        uploadService.updateRouteCover(routeId, file)
                )
        );
    }

    private static final Logger logger = LoggerFactory.getLogger(RoutesUploadController.class);

}
