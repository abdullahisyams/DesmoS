package com.example.desmosecommerce.service;

import com.example.desmosecommerce.entity.Product;

public class NameCriteria implements SearchCriteria {
    private final String name;
    public NameCriteria(String name) { this.name = name; }
    @Override
    public boolean matches(Product product) {
        return product.getName() != null && product.getName().toLowerCase().contains(name.toLowerCase());
    }
} 