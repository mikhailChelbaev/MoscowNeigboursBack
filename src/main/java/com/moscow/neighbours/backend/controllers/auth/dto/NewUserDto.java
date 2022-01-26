package com.moscow.neighbours.backend.controllers.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * New user credentials holder
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {
    private String email;
    private String password;

    @NotEmpty
    private String name;
}
