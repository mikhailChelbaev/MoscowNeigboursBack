package com.moscow.neighbours.backend.controllers.auth.service.interfaces;

import com.moscow.neighbours.backend.controllers.auth.dto.*;
import com.moscow.neighbours.backend.db.model.user.DBUser;

import java.util.List;

public interface IAuthService {

    // MARK: - create user

    DBUser createUser(NewUserDto newUser, List<String> roles);

    DBUser createOAuthUser(OAuthNewUserDto newUserDto, List<String> roles);

    // MARK: - authenticate user

    JWTResponse createJWTForDBUser(DBUser user);

    JWTResponse authenticateUser(LoginUserDto userDto);

    JWTResponse authenticateOAuthUser(OAuthLoginUserDto userDto);

    // MARK: - refresh token

    JWTResponse refreshToken(String token);

    // MARK: - confirm user

    DBUser confirmAccount(AccountConfirmationDto confirmationDto);

    boolean isVerificationEnabled();

}
