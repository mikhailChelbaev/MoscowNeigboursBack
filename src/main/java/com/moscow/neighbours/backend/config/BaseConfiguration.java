package com.moscow.neighbours.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Additional bean configuration
 */
@Configuration
public class  BaseConfiguration {
    @Bean("encoder")
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
