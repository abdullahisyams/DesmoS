package com.example.desmosecommerce.service;

public class WelcomeDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double total) {
        return total * 0.90; // 10% off
    }
} 