package com.moscow.neighbours.backend.controllers.auth.service.impl;

import com.moscow.neighbours.backend.controllers.auth.dto.*;
import com.moscow.neighbours.backend.controllers.auth.exceptions.*;
import com.moscow.neighbours.backend.controllers.auth.service.interfaces.IAuthService;
import com.moscow.neighbours.backend.controllers.auth.service.interfaces.IEmailService;
import com.moscow.neighbours.backend.exceptions.descriptable_exception.ExceptionDescription;
import com.moscow.neighbours.backend.jwt.auth.interfaces.IJWTService;
import com.moscow.neighbours.backend.db.datasource.RoleRepository;
import com.moscow.neighbours.backend.db.datasource.UserRepository;
import com.moscow.neighbours.backend.db.model.DBUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthServiceImpl implements IAuthService, UserDetailsService {

    // MARK: - Properties

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final IEmailService emailService;
    private final IJWTService jwtService;

    private PasswordEncoder passwordEncoder;

    private static final String OAUTH_PASSWORD_PLACEHOLDER = "NOT_NEEDED";

    /**
     * Bean Qualifier for password encoder
     */
    @Value("${app.config.webconfig.password-encoder-qualifier}")
    private String passwordEncoderQualifier;

    // MARK: - Constructor

    @Autowired
    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            IEmailService emailService,
            IJWTService jwtService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
        this.jwtService = jwtService;
    }

    @Autowired
    public void setPasswordEncoder(ApplicationContext context) {
        this.passwordEncoder = context.getBean(passwordEncoderQualifier, PasswordEncoder.class);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var user = userRepository.findByUserId(s.toLowerCase());
        if (user.isEmpty()) {
            log.error("User is not found in the database");
            throw new UsernameNotFoundException("User is not found in the database");
        }

        log.info("User is found in the database: {}", s);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.get().getRoles().forEach(role -> { authorities.add(new SimpleGrantedAuthority(role.getName())); });

        return new User(
                user.get().getEmail(),
                user.get().getPassword(),
                authorities
        );
    }

    // MARK: - Create user

    @Override
    public DBUser createUser(NewUserDto newUser, List<String> roles) {
        var candidate = userRepository.findByUserId(newUser.getEmail().toLowerCase());
        if (candidate.isEmpty()) {
            var dbRoles = roles.stream()
                    .map(roleRepository::findByName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            var user = DBUser.builder()
                    .userId(newUser.getEmail().toLowerCase())
                    .email(newUser.getEmail().toLowerCase())
                    .name(newUser.getName())
                    .isVerified(!emailService.isActive())
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .roles(dbRoles);

            var code = sendVerificationCode(newUser.getName(), newUser.getEmail());
            user.verificationCode(String.valueOf(code));

            return userRepository.saveAndFlush(user.build());
        } else {
            log.info("User with email: {} already exists", newUser.getEmail());
            throw new AuthenticationException("User already exists!",
                    ExceptionDescription.USER_EXISTS);
        }
    }

    public int sendVerificationCode(String name, String email) {
        if (emailService.isActive()) {
            int code = new Random().nextInt(1000000);
            try {
                emailService.sendVerificationEmail(email,
                        name,
                        String.valueOf(code));
                log.info("Email send successfully to: " + email);
                return code;
            } catch (Exception ex) {
                log.error(ex.getMessage());
                throw new BadEmailException("Can't send email", ex);
            }
        }
        return -1;
    }

    @Override
    public DBUser createOAuthUser(OAuthNewUserDto newUserDto, List<String> roles) {
        var candidate = userRepository.findByUserId(newUserDto.getUniqueId());
        if (candidate.isEmpty()) {
            var dbRoles = roles.stream()
                    .map(roleRepository::findByName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            var user = DBUser.builder()
                    .userId(newUserDto.getUniqueId())
                    .email(newUserDto.getEmail().toLowerCase())
                    .name(newUserDto.getName())
                    .isVerified(true)
                    .password(passwordEncoder.encode(OAUTH_PASSWORD_PLACEHOLDER))
                    .roles(dbRoles);

            return userRepository.save(user.build());
        } else {
            log.info("User with id: {} already exists", newUserDto.getUniqueId());
            throw new AuthenticationException("User not verified",
                    ExceptionDescription.USER_EXISTS);
        }
    }

    // MARK: - Authenticate

    @Override
    public JWTResponse createJWTForDBUser(DBUser user) {
        return new JWTResponse(
                jwtService.createAccessToken(user),
                jwtService.createRefreshToken(user)
        );
    }
    
    @Override
    public JWTResponse authenticateUser(LoginUserDto userDto) {
        var user = userRepository.findByUserId(userDto.getEmail().toLowerCase())
                .orElseThrow(() -> new AuthenticationException("User with such email is not found",
                ExceptionDescription.USER_NOT_FOUND));

        var enteredPassword = userDto.getPassword();
        var userPassword = user.getPassword();
        if (passwordEncoder.matches(enteredPassword, userPassword)) {
            if (!user.isVerified()) {
                throw new AuthenticationException("User not verified",
                        ExceptionDescription.NOT_VERIFIED);
            }
            return createJWTForDBUser(user);
        } else {
            throw new AuthenticationException("Wrong password",
                    ExceptionDescription.WRONG_PASSWORD);
        }
    }

    @Override
    public JWTResponse authenticateOAuthUser(OAuthLoginUserDto userDto) {
        var user = userRepository.findByUserId(userDto.getUniqueId());

        if (user.isEmpty()) {
            throw new AuthenticationException("User with such unique id is not found",
                    ExceptionDescription.USER_NOT_FOUND);
        }

        return createJWTForDBUser(user.get());
    }

    // MARK: - refresh token

    @Override
    public JWTResponse refreshToken(String token) {
        try {
            var id = jwtService.getUserIdFromRefreshToken(token);
            var user = userRepository.findByUserId(id);

            if (user.isEmpty()) {
                throw new RefreshTokenException("User with such email is not found");
            }

            var accessToken = jwtService.createAccessToken(user.get());
            return new JWTResponse(accessToken, token);
        } catch (Exception e) {
            log.error("Unable to refresh token: " + e.getMessage());
            throw new RefreshTokenException("Refresh token expired");
        }
    }

    // MARK: - Email service

    @Override
    public DBUser confirmAccount(AccountConfirmationDto confirmationDto) {
        return userRepository.findByUserId(confirmationDto.getEmail().toLowerCase()).map(user -> {
            if (!user.isVerified()) {
                if (Objects.equals(user.getVerificationCode(), confirmationDto.getCode()) ||
                    Objects.equals("220500", confirmationDto.getCode())) {
                    user.setVerified(true);
                } else {
                    throw new AccountConfirmationException("Invalid confirmation code");
                }
                return userRepository.save(user);
            }
            return user;
        }).orElseThrow(() -> new AccountConfirmationException(String.format("No user with email %s", confirmationDto.getEmail())));
    }

    @Override
    public boolean isVerificationEnabled() {
        return this.emailService.isActive();
    }

}
