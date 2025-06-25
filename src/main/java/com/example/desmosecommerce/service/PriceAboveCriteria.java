package com.example.desmosecommerce.service;

import com.example.desmosecommerce.entity.Product;

public class PriceAboveCriteria implements SearchCriteria {
    private final double threshold;
    public PriceAboveCriteria(double threshold) { this.threshold = threshold; }
    @Override
    public boolean matches(Product product) {
        return product.getPrice() != null && product.getPrice() > threshold;
    }
} 