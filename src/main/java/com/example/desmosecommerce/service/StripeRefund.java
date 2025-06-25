package com.example.desmosecommerce.service;

public class StripeRefund implements RefundStrategy {
    @Override
    public String refund(double amount) {
        return "Refunded $" + amount + " via Stripe.";
    }
} 