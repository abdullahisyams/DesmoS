package com.example.desmosecommerce.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/static-images")
public class StaticImageController {
    
    private static final Logger logger = LoggerFactory.getLogger(StaticImageController.class);
    
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getStaticImage(@PathVariable String filename) {
        logger.debug("Requesting static image: {}", filename);
        
        Resource resource = new ClassPathResource("static/images/" + filename);
        if (resource.exists()) {
            logger.debug("Static image found: {}", filename);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource);
        }
        
        logger.warn("Static image not found: {}", filename);
        return ResponseEntity.notFound().build();
    }
} 