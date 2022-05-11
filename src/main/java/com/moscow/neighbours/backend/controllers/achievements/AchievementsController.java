package com.moscow.neighbours.backend.controllers.achievements;

import com.moscow.neighbours.backend.controllers.achievements.dto.CompletedAchievementDto;
import com.moscow.neighbours.backend.controllers.achievements.service.IAchievementsLoader;
import com.moscow.neighbours.backend.controllers.achievements.service.IAchievementsStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(path="/api/v1/achievements")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@Slf4j
public class AchievementsController {
    private final IAchievementsStore achievementsStore;
    private final IAchievementsLoader achievementsLoader;

    @Autowired
    AchievementsController(
            IAchievementsStore achievementsService,
            IAchievementsLoader achievementsLoader) {
        this.achievementsStore = achievementsService;
        this.achievementsLoader = achievementsLoader;
    }

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAchievements(Principal principal) {
        String email = getUserEmail(principal);
        log.info("GET: /api/v1/achievements : {}", email);

        return ResponseEntity.ok(achievementsLoader.getAchievements(email));
    }

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveAchievement(
            Principal principal,
            @RequestBody @Valid CompletedAchievementDto completedAchievementDto) {
        String email = getUserEmail(principal);
        log.info("POST: /api/v1/achievements : {}", email);

        achievementsStore.saveAchievement(email, completedAchievementDto);

        return ResponseEntity.ok("Information stored successfully");
    }

    // MARK: - Helpers

    private String getUserEmail(Principal principal) {
        if (principal != null) {
            return principal.getName();
        } else {
            return null;
        }
    }
}
