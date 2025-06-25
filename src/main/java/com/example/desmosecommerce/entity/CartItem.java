package com.example.desmosecommerce.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private Product product; // This will be populated when fetching cart items
} 