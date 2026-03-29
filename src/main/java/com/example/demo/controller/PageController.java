package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/login")
    public String showLoginPage() {
        return "redirect:/login.html";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "redirect:/register.html";
    }

    @GetMapping("/homepage")
    public String showHomepage() {
        return "redirect:/homepage.html";
    }
}