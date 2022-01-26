package com.moscow.neighbours.backend.controllers.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthNewUserDto {
    private String uniqueId;
    private String email;
    private String name;
}
