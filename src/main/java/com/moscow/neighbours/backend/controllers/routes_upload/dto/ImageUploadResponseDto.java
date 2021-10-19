package com.moscow.neighbours.backend.controllers.routes_upload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ImageUploadResponseDto {
    private String imageUrl;
}
