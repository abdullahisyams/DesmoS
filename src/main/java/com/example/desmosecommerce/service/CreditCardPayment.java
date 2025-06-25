package com.example.desmosecommerce.service;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public String pay(double amount) {
        return "Paid $" + amount + " using Credit Card.";
    }
} 