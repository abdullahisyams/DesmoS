package com.example.desmosecommerce.service;

public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double total) {
        return total;
    }
} 