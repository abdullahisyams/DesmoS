package com.example.desmosecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/02homePage.html")
    public String oldHomePage() {
        return "redirect:/homecontroller/home";
    }
} 