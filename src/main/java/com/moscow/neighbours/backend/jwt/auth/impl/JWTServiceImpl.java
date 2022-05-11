package com.moscow.neighbours.backend.jwt.auth.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.moscow.neighbours.backend.db.model.user.DBRole;
import com.moscow.neighbours.backend.db.model.user.DBUser;
import com.moscow.neighbours.backend.jwt.auth.interfaces.IJWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTServiceImpl implements IJWTService {

    @Value("${app.jwt.access_expiration}")
    private long jwtAccessExpiration;
    @Value("${app.jwt.refresh_expiration}")
    private long jwtRefreshExpiration;

    private Algorithm algorithm;

    @Autowired
    public void setAlgorithm(ApplicationContext context) {
        algorithm = context.getBean("algorithm", Algorithm.class);
    }

    @Override
    public String createAccessToken(DBUser user) {
        return JWT.create()
                .withSubject(user.getUserId())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtAccessExpiration))
                .withClaim("roles", user.getRoles().stream()
                        .map(DBRole::getName)
                        .collect(Collectors.toList()))
                .sign(algorithm);
    }

    @Override
    public String createRefreshToken(DBUser user) {
        return JWT.create()
                .withSubject(user.getUserId())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtRefreshExpiration))
                .sign(algorithm);
    }

    @Override
    public String getUserIdFromRefreshToken(String token) throws JWTVerificationException {
        var verifier = JWT.require(algorithm).build();
        var decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

}
