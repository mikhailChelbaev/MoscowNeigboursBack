package com.moscow.neighbours.backend.controllers.achievements;

import com.moscow.neighbours.backend.controllers.achievements.service.IAchievementsService;
import com.moscow.neighbours.backend.controllers.user.service.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path="/api/v1/achievements")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@Slf4j
public class AchievementsController {
    private final IAchievementsService achievementsService;

    @Autowired
    AchievementsController(
            IAchievementsService achievementsService
    ) {
        this.achievementsService = achievementsService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAchievements(Principal principal) {
        String email = null;
        if (principal != null) {
            email = principal.getName();
        }
        log.info("POST: /api/v1/achievements : {}", email);
        return ResponseEntity.ok(achievementsService.getAchievements(email));
    }

}
