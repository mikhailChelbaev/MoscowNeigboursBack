package com.moscow.neighbours.backend.controllers.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception for indicating absence of user with given email
 */
public class NoUserWithSuchEmailException extends ResponseStatusException {
    public NoUserWithSuchEmailException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
