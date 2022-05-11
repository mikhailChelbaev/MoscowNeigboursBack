package com.moscow.neighbours.backend.jwt.auth.interfaces;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.moscow.neighbours.backend.db.model.user.DBUser;

public interface IJWTService {

    String createAccessToken(DBUser user);

    String createRefreshToken(DBUser user);

    String getUserIdFromRefreshToken(String refreshToken) throws JWTVerificationException;

}
