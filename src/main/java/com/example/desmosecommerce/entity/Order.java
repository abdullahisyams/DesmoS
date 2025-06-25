package com.example.desmosecommerce.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String status;
    private String discountCode;
    private String paymentType; // e.g., paypal, stripe, creditcard
    private String refundStatus; // e.g., NONE, REQUESTED, REFUNDED
} 