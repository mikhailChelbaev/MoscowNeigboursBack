package com.moscow.neighbours.backend.exceptions;

import com.moscow.neighbours.backend.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * Controller for catching exceptions and preventing 500 status code
 */
@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * Handles all exceptions that have {@link BaseException} superclass
     * @param ex exception
     * @param request request
     * @return {@link ResponseEntity} of {@link MessageResponse} with error
     */
    @ExceptionHandler(value = {BaseException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request
    ) {
        return new ResponseEntity<Object>(MessageResponse.of(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all exceptions that have {@link ConstraintViolationException} superclass
     * @param ex exception
     * @param request request
     * @return {@link ResponseEntity} of {@link MessageResponse} with error
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolation(
            RuntimeException ex, WebRequest request
    ) {
        return new ResponseEntity<Object>(MessageResponse.of(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles authentication exceptions
     * @param ex exception
     * @param request request
     * @return {@link ResponseEntity} of {@link MessageResponse} with error
     */
    @ExceptionHandler(value = {AuthenticationCredentialsNotFoundException.class})
    protected ResponseEntity<Object> handleNotAuth(
            RuntimeException ex, WebRequest request
    ) {
        return new ResponseEntity<Object>(MessageResponse.of("Authentication required"), HttpStatus.FORBIDDEN);
    }
}

