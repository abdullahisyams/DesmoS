package com.example.desmosecommerce.service;

import com.example.desmosecommerce.entity.CartItem;
import com.example.desmosecommerce.entity.Product;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import com.example.desmosecommerce.service.DiscountStrategy;
import com.example.desmosecommerce.service.NoDiscountStrategy;
import com.example.desmosecommerce.service.WelcomeDiscountStrategy;
import com.example.desmosecommerce.service.SummerDiscountStrategy;

@Service
public class CartService {
    
    private final List<CartItem> cartItems = new ArrayList<>();
    private final ProductService productService;
    private DiscountStrategy discountStrategy = new NoDiscountStrategy();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    public CartService(ProductService productService) {
        this.productService = productService;
    }
    
    public void addItem(CartItem item) {
        item.setId(idGenerator.getAndIncrement());
        cartItems.add(item);
    }
    
    public boolean removeItem(Long itemId) {
        return cartItems.removeIf(item -> item.getId().equals(itemId));
    }
    
    public List<CartItem> getCartItems() {
        List<CartItem> itemsWithProducts = new ArrayList<>();
        for (CartItem item : cartItems) {
            Product product = productService.findById(item.getProductId());
            if (product != null) {
                CartItem itemWithProduct = new CartItem();
                itemWithProduct.setId(item.getId());
                itemWithProduct.setUserId(item.getUserId());
                itemWithProduct.setProductId(item.getProductId());
                itemWithProduct.setQuantity(item.getQuantity());
                itemWithProduct.setProduct(product);
                itemsWithProducts.add(itemWithProduct);
            }
        }
        return itemsWithProducts;
    }
    
    public CartItem getCartItem(Long id) {
        return cartItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .map(item -> {
                    Product product = productService.findById(item.getProductId());
                    if (product != null) {
                        item.setProduct(product);
                    }
                    return item;
                })
                .orElse(null);
    }
    
    public void updateQuantity(Long itemId, int newQuantity) {
        cartItems.stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(newQuantity));
    }
    
    public void clearCart() {
        cartItems.clear();
        discountStrategy = new NoDiscountStrategy();
    }
    
    public double getTotal() {
        double subtotal = cartItems.stream()
                .mapToDouble(item -> {
                    Product product = productService.findById(item.getProductId());
                    return product != null ? product.getPrice() * item.getQuantity() : 0;
                })
                .sum();
        return discountStrategy.applyDiscount(subtotal);
    }
    
    public boolean applyDiscount(String code) {
        switch (code) {
            case "WELCOME10":
                discountStrategy = new WelcomeDiscountStrategy();
                return true;
            case "SUMMER20":
                discountStrategy = new SummerDiscountStrategy();
                return true;
            default:
                discountStrategy = new NoDiscountStrategy();
                return false;
        }
    }
    
    public String getDiscountType() {
        if (discountStrategy instanceof WelcomeDiscountStrategy) {
            return "WELCOME10";
        } else if (discountStrategy instanceof SummerDiscountStrategy) {
            return "SUMMER20";
        } else {
            return "NONE";
        }
    }
    
    public double getDiscountPercentage() {
        if (discountStrategy instanceof WelcomeDiscountStrategy) {
            return 0.10;
        } else if (discountStrategy instanceof SummerDiscountStrategy) {
            return 0.20;
        } else {
            return 0.0;
        }
    }
} 