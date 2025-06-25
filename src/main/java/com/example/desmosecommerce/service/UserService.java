package com.example.desmosecommerce.service;

import com.example.desmosecommerce.dao.UserMapper;
import com.example.desmosecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserMapper userMapper;
    
    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    
    public User login(String email, String password) {
        return userMapper.findByEmailAndPassword(email, password);
    }
    
    public User register(User user) {
        // Validate email format
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        // Check if email already exists
        if (userMapper.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already registered");
        }
        
        // Validate password strength
        if (!isValidPassword(user.getPassword())) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain at least one number");
        }
        
        // Set default role
        user.setIsAdmin(false);
        
        userMapper.insert(user);
        return user;
    }
    
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 8 && password.matches(".*\\d.*");
    }
    
    public boolean isAdmin(String email) {
        User user = userMapper.findByEmail(email);
        return user != null && user.getIsAdmin();
    }
} 