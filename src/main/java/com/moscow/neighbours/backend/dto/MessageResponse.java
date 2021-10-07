package com.moscow.neighbours.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class MessageResponse {
    private String message;
}
