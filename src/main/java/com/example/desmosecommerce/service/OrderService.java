package com.example.desmosecommerce.service;

import com.example.desmosecommerce.entity.Order;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    
    private final List<Order> orders = new ArrayList<>();
    
    public void save(Order order) {
        order.setOrderDate(LocalDateTime.now());
        orders.add(order);
    }
    
    public List<Order> findAll() {
        return new ArrayList<>(orders);
    }
    
    public Order findById(Long id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public List<Order> findByUserId(Long userId) {
        List<Order> userOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getId() != null && order.getId().equals(userId)) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    public void updateRefundStatus(Long orderId, String refundStatus) {
        Order order = findById(orderId);
        if (order != null) {
            order.setRefundStatus(refundStatus);
        }
    }
} 