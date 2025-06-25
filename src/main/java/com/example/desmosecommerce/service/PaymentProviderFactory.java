package com.example.desmosecommerce.service;

public interface PaymentProviderFactory {
    PaymentStrategy createPayment();
    RefundStrategy createRefund();
} 