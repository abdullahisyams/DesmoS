package com.example.desmosecommerce.service;

public class PaymentFactory {
    public static PaymentProviderFactory getProviderFactory(String type) {
        switch (type.toLowerCase()) {
            case "paypal":
                return new PaypalFactory();
            case "stripe":
                return new StripeFactory();
            case "creditcard":
                return new CreditCardFactory();
            default:
                throw new IllegalArgumentException("Unknown payment type: " + type);
        }
    }
} 