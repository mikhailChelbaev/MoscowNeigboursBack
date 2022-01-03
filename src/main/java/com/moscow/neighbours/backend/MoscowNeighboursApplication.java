package com.moscow.neighbours.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MoscowNeighboursApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoscowNeighboursApplication.class, args);
	}

	@Bean
    PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
    }

}
