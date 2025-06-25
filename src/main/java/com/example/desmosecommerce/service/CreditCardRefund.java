package com.example.desmosecommerce.service;

public class CreditCardRefund implements RefundStrategy {
    @Override
    public String refund(double amount) {
        return "Refunded $" + amount + " via Credit Card.";
    }
} 