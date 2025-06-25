package com.example.desmosecommerce.service;

public class CreditCardFactory implements PaymentProviderFactory {
    @Override
    public PaymentStrategy createPayment() {
        return new CreditCardPayment();
    }
    @Override
    public RefundStrategy createRefund() {
        return new CreditCardRefund();
    }
} 