package com.moscow.neighbours.backend.controllers.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RefreshTokenException extends ResponseStatusException {
    public RefreshTokenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
