package com.moscow.neighbours.backend.controllers.images.service.impl;

import com.moscow.neighbours.backend.controllers.images.exceptions.FileNotFoundException;
import com.moscow.neighbours.backend.controllers.images.service.interfaces.IImageDownloadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageDownloadServiceImpl implements IImageDownloadService {

    @Value("${app.upload-folder}")
    public static String UPLOAD_FOLDER;

    @Override
    public Resource getImage(String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_FOLDER)
                    .toAbsolutePath()
                    .resolve(fileName)
                    .normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }

}
