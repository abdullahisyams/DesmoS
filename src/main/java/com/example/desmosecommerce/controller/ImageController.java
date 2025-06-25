package com.example.desmosecommerce.controller;

import com.example.desmosecommerce.service.ImageServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/images")
public class ImageController {
    
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    private final ImageServiceProxy imageService;
    
    @Autowired
    public ImageController(ImageServiceProxy imageService) {
        this.imageService = imageService;
    }
    
    @GetMapping("/{filename:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        logger.debug("Requesting product image: {}", filename);
        
        if (filename == null || filename.trim().isEmpty()) {
            logger.warn("Empty filename requested");
            return ResponseEntity.notFound().build();
        }
        
        // Log the full URL being requested
        logger.debug("Full image URL being requested: /images/{}", filename);

        System.out.println("ini adalah gambar"+filename);
        byte[] imageData = imageService.getImageData(filename);
        if (imageData != null) {
            logger.debug("Product image found and served: {}", filename);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imageData);
        }
        
        logger.warn("Product image not found: {}", filename);
        return ResponseEntity.notFound().build();
    }
} 