package com.moscow.neighbours.backend.controllers.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountConfirmationDto {
    @NotNull
    private String email;

    @NotNull
    private String code;
}
