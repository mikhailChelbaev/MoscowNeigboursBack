package com.moscow.neighbours.backend.controllers.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthLoginUserDto {
    private String uniqueId;
}
