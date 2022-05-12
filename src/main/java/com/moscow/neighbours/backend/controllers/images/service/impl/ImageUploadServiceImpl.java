package com.moscow.neighbours.backend.controllers.images.service.impl;

import com.moscow.neighbours.backend.controllers.images.service.interfaces.IImageUploadService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageUploadServiceImpl implements IImageUploadService {

    @Value("${app.upload-folder}")
    public String UPLOAD_FOLDER;

    /**
     * Saves given file
     * @param file file to save
     * @param id saved file name
     * @return {@link Path} where file was saved
     * @throws IOException if cant create file
     */
    public Path saveUploadedFile(MultipartFile file, String id) throws IOException {
        byte[] bytes = file.getBytes();

        // make dir if needed
        var pathToDir = new File(UPLOAD_FOLDER);
        if (!pathToDir.exists()) {
            pathToDir.mkdirs();
        }

        Path path = Paths.get(UPLOAD_FOLDER + id + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
        Files.write(path, bytes);

        return path;
    }

    /**
     * Deletes file
     * @param path file to be deleted path
     */
    public void deleteFile(String path) {
        try {
            Files.delete(Paths.get(UPLOAD_FOLDER + path));
        } catch (Exception ex) {
            logger.info(String.format("Can't delete file: %s", UPLOAD_FOLDER + path));
        }
    }

    @Override
    public String getUploadUri(Path path) {
        String fileName = path.getFileName().toString();
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/")
                .path(fileName)
                .toUriString();
    }

    private static final Logger logger = LoggerFactory.getLogger(ImageUploadServiceImpl.class);

}

