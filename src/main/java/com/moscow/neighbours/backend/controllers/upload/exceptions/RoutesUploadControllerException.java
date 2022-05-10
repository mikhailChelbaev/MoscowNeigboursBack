package com.moscow.neighbours.backend.controllers.upload.exceptions;

import com.moscow.neighbours.backend.exceptions.BaseException;

public class RoutesUploadControllerException extends BaseException {
    public RoutesUploadControllerException(String message) {
        super(message);
    }

    public RoutesUploadControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}
