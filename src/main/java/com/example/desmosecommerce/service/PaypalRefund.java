package com.example.desmosecommerce.service;

public class PaypalRefund implements RefundStrategy {
    @Override
    public String refund(double amount) {
        return "Refunded $" + amount + " via PayPal.";
    }
} 