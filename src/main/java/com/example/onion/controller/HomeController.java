package com.example.onion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home";  // home.html 템플릿을 반환
    }

    @GetMapping("/login")
    public String login() {
        return "/common/login";  // 로그인 페이지
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";  // 접근 거부 페이지
    }
}

