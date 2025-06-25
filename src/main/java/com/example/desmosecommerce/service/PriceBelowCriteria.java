package com.example.desmosecommerce.service;

import com.example.desmosecommerce.entity.Product;

public class PriceBelowCriteria implements SearchCriteria {
    private final double threshold;
    public PriceBelowCriteria(double threshold) { this.threshold = threshold; }
    @Override
    public boolean matches(Product product) {
        return product.getPrice() != null && product.getPrice() <= threshold;
    }
} 