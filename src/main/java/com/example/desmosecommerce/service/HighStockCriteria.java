package com.example.desmosecommerce.service;

import com.example.desmosecommerce.entity.Product;

public class HighStockCriteria implements SearchCriteria {
    private final int threshold;
    public HighStockCriteria(int threshold) { this.threshold = threshold; }
    @Override
    public boolean matches(Product product) {
        return product.getStock() != null && product.getStock() > threshold;
    }
} 