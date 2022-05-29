package com.moscow.neighbours.backend.controllers.user;

import com.moscow.neighbours.backend.controllers.user.service.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path="/api/v1/me")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@Slf4j
public class UserController {

    private final IUserService userService;

    @Autowired
    UserController(
            IUserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserInfo(Principal principal) {
        String email = null;
        if (principal != null) {
            email = principal.getName();
        }
        log.info("GET: /api/v1/me : {}", email);
        return ResponseEntity.ok(userService.getUserInfo(email));
    }

}
