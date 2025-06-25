package com.example.desmosecommerce.service;

public class PaypalPayment implements PaymentStrategy {
    @Override
    public String pay(double amount) {
        return "Paid $" + amount + " using PayPal.";
    }
} 