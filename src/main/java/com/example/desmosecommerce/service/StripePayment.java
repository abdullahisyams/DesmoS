package com.example.desmosecommerce.service;

public class StripePayment implements PaymentStrategy {
    @Override
    public String pay(double amount) {
        return "Paid $" + amount + " using Stripe.";
    }
} 