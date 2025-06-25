package com.example.desmosecommerce.service;

import com.example.desmosecommerce.entity.Product;

public class LowStockCriteria implements SearchCriteria {
    private final int threshold;
    public LowStockCriteria(int threshold) { this.threshold = threshold; }
    @Override
    public boolean matches(Product product) {
        return product.getStock() != null && product.getStock() <= threshold;
    }
} 