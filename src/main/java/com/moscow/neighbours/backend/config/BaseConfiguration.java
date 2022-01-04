package com.moscow.neighbours.backend.config;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Additional bean configuration
 */
@Configuration
public class  BaseConfiguration {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Bean("encoder")
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("algorithm")
    Algorithm algorithm() {
        return Algorithm.HMAC256(jwtSecret.getBytes());
    }
}
