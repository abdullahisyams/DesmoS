package com.example.desmosecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ImageServiceProxy implements ImageService {
    
    private final ImageService imageService;
    private final ConcurrentHashMap<String, byte[]> imageCache;
    
    @Autowired
    public ImageServiceProxy(FileSystemImageService imageService) {
        this.imageService = imageService;
        this.imageCache = new ConcurrentHashMap<>();
    }
    
    @Override
    public String getImageUrl(String imagePath) {
        // Add logging or validation if needed
        return imageService.getImageUrl(imagePath);
    }
    
    @Override
    public byte[] getImageData(String imagePath) {
        // Check cache first
        return imageCache.computeIfAbsent(imagePath, key -> {
            byte[] data = imageService.getImageData(key);
            if (data != null) {
                return data;
            }
            return null;
        });
    }
} 