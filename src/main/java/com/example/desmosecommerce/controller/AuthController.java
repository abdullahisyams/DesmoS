package com.example.desmosecommerce.controller;

import com.example.desmosecommerce.entity.User;
import com.example.desmosecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/authcontroller")
@Controller
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private static final String ADMIN_EMAIL = "admin@desmos.com";
    private static final String ADMIN_PASSWORD = "admin123";

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String customerLoginPage() {
        return "login";
    }

    @GetMapping("/admin/login")
    public String adminLoginPage(HttpSession session) {
        // If already logged in as admin, redirect to dashboard
        if (isAdminLoggedIn(session)) {
            return "redirect:/admin/dashboard";
        }
        return "admin/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/login")
    public String customerLogin(@RequestParam String email, 
                              @RequestParam String password,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        try {
            User user = userService.login(email, password);
            if (user != null) {
                session.setAttribute("isLoggedIn", true);
                session.setAttribute("userId", user.getId());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("fullname", user.getFullname());
                session.setAttribute("isAdmin", false);
                return "redirect:/maincontroller/shop";
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid email or password");
                return "redirect:/authcontroller/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Login failed: " + e.getMessage());
            return "redirect:/authcontroller/login";
        }
    }

    @PostMapping("/admin/login")
    public String adminLogin(@RequestParam String email,
                           @RequestParam String password,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        logger.info("Attempting admin login with email: {}", email);
        
        if (ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(password)) {
            logger.info("Admin login successful");
            session.setAttribute("isLoggedIn", true);
            session.setAttribute("email", email);
            session.setAttribute("fullname", "Admin");
            session.setAttribute("isAdmin", true);
            return "redirect:/admin/dashboard";
        } else {
            logger.warn("Admin login failed for email: {}", email);
            redirectAttributes.addFlashAttribute("error", "Invalid admin credentials");
            return "redirect:/authcontroller/admin/login";
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam String fullname,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String confirmPassword,
                          RedirectAttributes redirectAttributes) {
        try {
            if (!password.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "Passwords do not match");
                return "redirect:/authcontroller/register";
            }

            User user = new User();
            user.setFullname(fullname);
            user.setEmail(email);
            user.setPassword(password);

            userService.register(user);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/authcontroller/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/authcontroller/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/admin/logout")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    private boolean isAdminLoggedIn(HttpSession session) {
        return session.getAttribute("isAdmin") != null && (Boolean) session.getAttribute("isAdmin");
    }
} 