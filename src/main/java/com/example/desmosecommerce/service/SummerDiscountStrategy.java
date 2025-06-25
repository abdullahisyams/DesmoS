package com.example.desmosecommerce.service;

public class SummerDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double total) {
        return total * 0.80; // 20% off
    }
} 