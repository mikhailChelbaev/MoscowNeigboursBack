package com.moscow.neighbours.backend.controllers.auth.exceptions;

import com.moscow.neighbours.backend.exceptions.BaseException;

public class BadEmailException extends BaseException {
    public BadEmailException(String message) {
        super(message);
    }

    public BadEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
