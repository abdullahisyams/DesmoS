package com.example.desmosecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/homecontroller")
@Controller
public class HomeController {

    @GetMapping("/")
    public String root() {
        return "redirect:/homecontroller/home";
    }

    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model) {
        System.out.println(">>> HomeController dipanggilllllllllllllll!");
        model.addAttribute("request", request);
        return "home";
    }

    @GetMapping("/02homePage.html")
    public String oldHomePage() {
        return "redirect:/homecontroller/home";
    }
} 