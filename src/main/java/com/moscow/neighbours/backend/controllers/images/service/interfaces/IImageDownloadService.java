package com.moscow.neighbours.backend.controllers.images.service.interfaces;

import org.springframework.core.io.Resource;

public interface IImageDownloadService {

    Resource getImage(String filename);

}
