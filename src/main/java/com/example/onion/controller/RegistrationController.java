package com.example.onion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import com.example.onion.dto.UserInfoDTO;
import com.example.onion.entity.Userinfo;
import com.example.onion.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RegistrationController {

	@Autowired
	private UserService userService;

	// 회원가입 폼 페이지 반환
	@GetMapping("member/registerForm")
	public String showRegistrationForm() {
		return "member/register"; // 회원가입 페이지를 반환
	}
	
	@GetMapping("/checkId")
    public String checkId(@RequestParam("id") String id, Model model) {
        boolean result = userService.isExistId(id);
        model.addAttribute("id", id);
        model.addAttribute("result", result);
        return "member/checkId"; // 아이디 중복 확인 결과를 반환하는 페이지
    }
	
	// 회원가입 처리
    @PostMapping("/register")
    public RedirectView registerUser(@ModelAttribute Userinfo user) {
        System.out.println("회원가입 정보: " + user);
        
        // UserService에서 암호화 후 저장
        userService.registerUser(user); 
        System.out.println("저장된 엔티티: " + user);

        return new RedirectView("/loginForm"); // 회원가입 후 로그인 페이지로 리다이렉트
    }
}
