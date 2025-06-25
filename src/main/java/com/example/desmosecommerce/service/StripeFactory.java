package com.example.desmosecommerce.service;

public class StripeFactory implements PaymentProviderFactory {
    @Override
    public PaymentStrategy createPayment() {
        return new StripePayment();
    }
    @Override
    public RefundStrategy createRefund() {
        return new StripeRefund();
    }
} 