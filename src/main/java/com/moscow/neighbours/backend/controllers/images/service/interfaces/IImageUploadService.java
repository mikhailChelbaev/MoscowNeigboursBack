package com.moscow.neighbours.backend.controllers.images.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface IImageUploadService {

    Path saveUploadedFile(MultipartFile file, String id) throws IOException;

    void deleteFile(String path);

    String getUploadUri(Path path);

}
