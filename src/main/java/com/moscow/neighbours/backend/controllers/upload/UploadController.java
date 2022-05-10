package com.moscow.neighbours.backend.controllers.upload;

import com.moscow.neighbours.backend.controllers.routes.dto.RouteDto;
import com.moscow.neighbours.backend.controllers.upload.dto.AchievementUploadDto;
import com.moscow.neighbours.backend.controllers.upload.dto.ImageUploadResponseDto;
import com.moscow.neighbours.backend.controllers.upload.service.interfaces.IAchievementsUploadService;
import com.moscow.neighbours.backend.controllers.upload.service.interfaces.IRoutesUploadService;
import com.moscow.neighbours.backend.controllers.upload.service.interfaces.IUploadRouteImagesService;
import com.moscow.neighbours.backend.dto.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import javax.validation.Valid;

@RestController
@RequestMapping(path="/api/hidden/upload")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@Slf4j
public class UploadController {

    private final IRoutesUploadService routesUploadService;
    private final IUploadRouteImagesService imagesUploadService;
    private final IAchievementsUploadService achievementsUploadService;

    @Autowired
    public UploadController(
            IRoutesUploadService routesUploadService,
            IUploadRouteImagesService imagesUploadService,
            IAchievementsUploadService achievementsUploadService
    ) {
        this.routesUploadService = routesUploadService;
        this.imagesUploadService = imagesUploadService;
        this.achievementsUploadService = achievementsUploadService;
    }

    // MARK: - Routes

    @PostMapping("/routes")
    public ResponseEntity<?> uploadRoutes(@RequestBody List<RouteDto> routesUploadDtoList) {
        log.info("POST: /api/hidden/upload/routes");
        routesUploadService.saveRoutes(routesUploadDtoList);
        return ResponseEntity.ok(MessageResponse.of("Routes uploaded successfully"));
    }

    @RequestMapping(value = "/person/avatar", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<?> uploadPersonAvatar(
            @RequestParam("person_id") UUID personId,
            @RequestPart("file") @Valid MultipartFile file
    ) {
        log.info("POST: /api/hidden/upload/person/avatar{}", personId);

        return ResponseEntity.ok(
                ImageUploadResponseDto.of(
                        imagesUploadService.updatePersonAvatar(personId, file)
                )
        );
    }

    @RequestMapping(value = "/route/cover", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<?> uploadRouteCover(
            @RequestParam("route_id") UUID routeId,
            @RequestPart("file") @Valid MultipartFile file
    ) {
        log.info("POST: /api/hidden/upload/route/cover{}", routeId);

        return ResponseEntity.ok(
                ImageUploadResponseDto.of(
                        imagesUploadService.updateRouteCover(routeId, file)
                )
        );
    }

    // MARK: - Achievements

    @PostMapping("/achievements")
    public ResponseEntity<?> uploadAchievements(@RequestBody List<AchievementUploadDto> achievements) {
        log.info("POST: /api/hidden/upload/achievements");
        achievementsUploadService.saveAchievements(achievements);
        return ResponseEntity.ok(MessageResponse.of("Achievements uploaded successfully"));
    }

}
