package com.example.desmosecommerce.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FileSystemImageService implements ImageService {
    
    private static final Logger logger = LoggerFactory.getLogger(FileSystemImageService.class);
    
    @Value("${upload.path}")
    private String uploadPath;
    
    @Override
    public String getImageUrl(String imagePath) {
        logger.debug("Getting image URL for path: {}", imagePath);
        
        if (imagePath == null || imagePath.trim().isEmpty()) {
            logger.warn("Empty image path provided, returning default image");
            return "/static-images/placeholder.png";
        }
        
        // Remove 'uploads/' prefix if it exists
        String path = imagePath.startsWith("uploads/") ? imagePath.substring(8) : imagePath;
        String url = "/images/" + path;
        logger.debug("Generated image URL: {}", url);
        return url;
    }
    
    @Override
    public byte[] getImageData(String imagePath) {
        logger.debug("Getting image data for path: {}", imagePath);
        
        try {
            if (imagePath == null || imagePath.trim().isEmpty()) {
                logger.warn("Empty image path provided");
                return null;
            }
            
            // First try to get the image from the uploads directory
            Path uploadDir = Paths.get(uploadPath).toAbsolutePath();
            logger.debug("Upload directory: {}", uploadDir);
            
            if (!Files.exists(uploadDir)) {
                logger.warn("Upload directory does not exist: {}", uploadDir);
                Files.createDirectories(uploadDir);
                logger.info("Created upload directory: {}", uploadDir);
            }
            
            // Get the image file path
            Path imageFilePath = uploadDir.resolve(imagePath);
            logger.debug("Image file path: {}", imageFilePath);
            
            if (Files.exists(imageFilePath)) {
                logger.debug("Image file found in uploads: {}", imageFilePath);
                return Files.readAllBytes(imageFilePath);
            }
            
            // If not found in uploads, try to get from static resources
            try {
                String staticPath = "static/images/" + imagePath;
                logger.debug("Trying to find image in static resources: {}", staticPath);
                ClassPathResource resource = new ClassPathResource(staticPath);
                if (resource.exists()) {
                    logger.debug("Image file found in static resources: {}", staticPath);
                    return resource.getInputStream().readAllBytes();
                }
            } catch (IOException e) {
                logger.debug("Image not found in static resources: {}", imagePath);
            }
            
            logger.warn("Image file not found in either location: {}", imagePath);
            return null;
        } catch (IOException e) {
            logger.error("Error reading image file: {}", e.getMessage(), e);
            return null;
        }
    }
} 