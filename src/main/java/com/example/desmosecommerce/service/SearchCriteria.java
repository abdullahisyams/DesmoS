package com.example.desmosecommerce.service;

import com.example.desmosecommerce.entity.Product;

public interface SearchCriteria {
    boolean matches(Product product);
} 