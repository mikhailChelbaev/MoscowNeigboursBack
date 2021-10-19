package com.moscow.neighbours.backend.controllers.images.exceptions;

import com.moscow.neighbours.backend.exceptions.BaseException;

public class FileNotFoundException extends BaseException {
    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}