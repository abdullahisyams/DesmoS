package com.example.desmosecommerce.service;

public class PaypalFactory implements PaymentProviderFactory {
    @Override
    public PaymentStrategy createPayment() {
        return new PaypalPayment();
    }
    @Override
    public RefundStrategy createRefund() {
        return new PaypalRefund();
    }
} 