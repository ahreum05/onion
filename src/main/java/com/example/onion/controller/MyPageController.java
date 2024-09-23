package com.example.onion.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.onion.dto.UserInfoDTO;
import com.example.onion.entity.Userinfo;
import com.example.onion.service.UserService;

@Controller
public class MyPageController {
	
	@Autowired
	private UserService userService; // UserService 주입
	
	@Value("${project.upload.path}") // 절대 경로로 설정된 업로드 경로를 가져옵니다.
	private String uploadPath;
	
	@GetMapping("/user/mypage")
	public String myPage(Model model) {
		// 현재 로그인된 사용자 정보 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String uid = authentication.getName();
		
		// 사용자 정보를 가져와 모델에 추가
		UserInfoDTO user = userService.getMemberById(uid); // User 서비스에서 유저 정보 가져오기
		model.addAttribute("user", user);
		
		if (user == null) {
			return "redirect:/loginForm"; // 유저가 없으면 로그인 페이지로 리다이렉트
		}

		// 마이페이지 템플릿 호출
		return "user/mypage"; // src/main/resources/templates/user/mypage.html
	}

	@GetMapping("/user/updateForm")
	public String updateForm(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String uid = authentication.getName();
		
		// 사용자 정보를 가져와 모델에 추가
		UserInfoDTO user = userService.getMemberById(uid);
		model.addAttribute("user", user);

		return "user/updateForm"; // 수정 폼 페이지 반환
	}

    @PostMapping("/user/update")
	public String uploadProfilePicture(
	        @RequestParam("profilePicture") MultipartFile uploadFile,
	        RedirectAttributes redirectAttributes,
	        @ModelAttribute UserInfoDTO userDto,
	        Model model) {

	    // 현재 로그인된 사용자 정보 가져오기
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String uid = authentication.getName();
	    
	    // 사용자 정보 불러오기
	    UserInfoDTO user = userService.getMemberById(uid);
	    if (user == null) {
	        model.addAttribute("error", "사용자 정보를 찾을 수 없습니다.");
	        return "user/updateForm"; // 사용자 정보가 없을 경우 수정 페이지로 이동
	    }

	    // 프로필 이미지 파일명 설정
	    String fileName = uploadFile.getOriginalFilename();
	    
	    if (fileName != null && !uploadFile.isEmpty()) {
	        userDto.setProfile_image(fileName);
	        
	        // 절대 경로로 파일 저장 경로 설정 및 파일 저장
	        String filePath = uploadPath + File.separator + fileName;
	        System.out.println("test1" + filePath);
	        File file = new File(filePath);
	        try {
	            uploadFile.transferTo(file);
	        } catch (IOException e) {
	            e.printStackTrace();
	            model.addAttribute("error", "파일 업로드에 실패했습니다.");
	            return "user/updateForm"; // 파일 업로드 실패 시 수정 페이지로 이동
	        }
	    }
	    
	    // 사용자 정보 업데이트
	    boolean isUpdated = userService.updateMember(userDto);
	    if (isUpdated) {
	        redirectAttributes.addFlashAttribute("message", "회원 정보가 성공적으로 수정되었습니다.");
	        model.addAttribute("isUpdated", isUpdated);
	        return "redirect:/user/mypage";  // 수정 성공 시 마이페이지로 리다이렉트
	    } else {
	        model.addAttribute("error", "업데이트에 실패했습니다.");
	        return "user/updateForm"; // 업데이트 실패 시 수정 페이지로 이동
	    }
	}
}
