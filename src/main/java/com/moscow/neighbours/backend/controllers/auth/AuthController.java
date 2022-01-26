package com.moscow.neighbours.backend.controllers.auth;

import com.moscow.neighbours.backend.controllers.auth.dto.*;
import com.moscow.neighbours.backend.controllers.auth.service.interfaces.IAuthService;
import com.moscow.neighbours.backend.controllers.auth.service.interfaces.IEmailService;
import com.moscow.neighbours.backend.controllers.user.dto.UserDto;
import com.moscow.neighbours.backend.dto.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path="/api/v1/auth")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@Slf4j
public class AuthController {

    private final IAuthService authService;
    private final IEmailService emailService;

    AuthController(
            IAuthService authService,
            IEmailService emailService
    ) {
        this.authService = authService;
        this.emailService = emailService;
    }

    // MARK: - sing in

    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginUserDto user) {
        log.info("POST: /api/v1/auth/signIn : {}", user.getEmail());
        return ResponseEntity.ok(this.authService.authenticateUser(user));
    }

    @PostMapping("/oauth/signIn")
    public ResponseEntity<?> authenticateOAuthUser(@RequestBody @Valid OAuthLoginUserDto user) {
        log.info("POST: /api/v1/auth/oauth/signIn: {}", user.getUniqueId());
        return ResponseEntity.ok(this.authService.authenticateOAuthUser(user));
    }

    // MARK: - sing up

    @PostMapping("/signUp")
    public ResponseEntity<?> signupUser(@RequestBody @Valid NewUserDto userDto) {
        log.info("POST: /api/v1/auth/signup : {}", userDto.getEmail());
        var user = authService.createUser(userDto, List.of("ROLE_USER"));

        var userResponse = UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .isVerified(!authService.isVerificationEnabled())
                .build();

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/oauth/signUp")
    public ResponseEntity<?> signupOAuthUser(@RequestBody @Valid OAuthNewUserDto userDto) {
        log.info("POST: /api/v1/auth/signUp: {}", userDto.getEmail());
        var user = authService.createOAuthUser(userDto, List.of("ROLE_USER"));
        return ResponseEntity.ok(this.authService.createJWTForDBUser(user));
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmAccount(@RequestBody @Valid AccountConfirmationDto confirmationDto) {
        log.info("POST: /api/v1/auth/confirm: {}", confirmationDto.toString());
        var user = authService.confirmAccount(confirmationDto);
        return ResponseEntity.ok(authService.createJWTForDBUser(user));
    }

    // MARK: - refresh token

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody @Valid String token) {
        log.info("POST: /api/v1/auth/refreshToken");
        return ResponseEntity.ok(this.authService.refreshToken(token.replace("\"", "")));
    }


}
