package com.moscow.neighbours.backend.dto;

import com.moscow.neighbours.backend.exceptions.descriptable_exception.ExceptionDescription;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class ErrorWithDescriptionResponse {
    private String message;
    private ExceptionDescription description;
}