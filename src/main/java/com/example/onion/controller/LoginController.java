package com.example.onion.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.onion.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
   
   @Autowired
    private UserService userService;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "member/login"; 
    }
    
    @GetMapping("/member/logout")
    public String logout(HttpServletRequest request) {
        // 로그아웃은 Spring Security가 처리하므로 컨트롤러에서 별도 처리 불필요
        return "redirect:/main";
    }
    
    @GetMapping("/member/checkId")
    public String checkId(@RequestParam("userid") String userid, Model model) {
        boolean exist = userService.isExistId(userid);  // 유저 ID 존재 여부 확인
        model.addAttribute("userid", userid);
        model.addAttribute("result", exist);
        return "member/checkId";
    }

}

