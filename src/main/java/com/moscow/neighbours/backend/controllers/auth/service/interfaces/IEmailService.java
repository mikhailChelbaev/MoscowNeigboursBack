package com.moscow.neighbours.backend.controllers.auth.service.interfaces;

public interface IEmailService {
    public void sendVerificationEmail(String email, String name, String code) throws Exception;

    boolean isActive();
}
