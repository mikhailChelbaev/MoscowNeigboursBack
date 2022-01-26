package com.moscow.neighbours.backend.controllers.auth.exceptions;

import com.moscow.neighbours.backend.exceptions.descriptable_exception.DescriptableException;
import com.moscow.neighbours.backend.exceptions.descriptable_exception.ExceptionDescription;

public class AuthenticationException extends DescriptableException {
    public AuthenticationException(String message, ExceptionDescription description) {
        super(message, description);
    }
}
