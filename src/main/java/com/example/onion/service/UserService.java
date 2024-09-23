package com.example.onion.service;

import com.example.onion.dto.UserInfoDTO;
import com.example.onion.entity.Userinfo;
import com.example.onion.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Value("${project.upload.path}")  // application.properties에서 설정한 경로를 주입
    private String uploadDir;

    // 사용자 정보 저장
    public void registerUser(Userinfo user) {
    	user.setRole("USER");
        // 비밀번호를 항상 암호화하여 저장
    	System.out.println("user1 = "+user);
        user.setUpwd(passwordEncoder.encode(user.getUpwd()));
        user.setUlogtime(LocalDateTime.now());
        userRepository.save(user);
    }

    public boolean isExistId(String uid) {
        return userRepository.existsById(uid);
    }

    public UserInfoDTO getMemberById(String uid) {
        return userRepository.findById(uid)
                .map(user -> new UserInfoDTO(
                    user.getUserid(), 
                    user.getUname(), 
                    null, // 비밀번호는 반환하지 않음
                    user.getUgender(), 
                    user.getUjumin1(), 
                    user.getUjumin2(), 
                    user.getUtel1(), 
                    user.getUtel2(), 
                    user.getUtel3(), 
                    user.getUaddr(), 
                    user.getUemail1(), 
                    user.getUemail2(), 
                    user.getUlogtime(), 
                    user.getIs_active(), 
                    user.getLast_login_time(), 
                    user.getProfile_image(), 
                    user.getRole()
                )).orElse(null); 
    }

    // 회원 정보 수정
    public boolean updateMember(UserInfoDTO dto) {
        Userinfo user = userRepository.findById(dto.getUserid()).orElse(null);
        if (user != null) {
        	// 이미지 업데이트
        	user.setProfile_image(dto.getProfile_image());
        	
        	// 이름 업데이트
            user.setUname(dto.getUname());

            // 이메일 업데이트
            user.setUemail1(dto.getUemail1());
            user.setUemail2(dto.getUemail2());
            
            // 전화번호 업데이트 (널 값 체크)
            user.setUtel1(dto.getUtel1() != null ? dto.getUtel1() : user.getUtel1());
            user.setUtel2(dto.getUtel2() != null ? dto.getUtel2() : user.getUtel2());
            user.setUtel3(dto.getUtel3() != null ? dto.getUtel3() : user.getUtel3());

            // 비밀번호 변경 요청이 있을 때에만 처리
            if (dto.getUpwd() != null && !dto.getUpwd().isEmpty()) {
                user.setUpwd(passwordEncoder.encode(dto.getUpwd()));
            }
            
            // 주소 업데이트 (널 값 체크)
            user.setUaddr(dto.getUaddr() != null ? dto.getUaddr() : user.getUaddr());

            userRepository.save(user);
            return true;
        }
        return false;
    }
    



    
//    public void saveProfilePicture(MultipartFile profilePicture) throws IOException {
//        String loggedInUserId = SecurityContextHolder.getContext().getAuthentication().getName();
//        Userinfo user = userRepository.findByUserid(loggedInUserId).orElseThrow(() -> new RuntimeException("User not found"));
//
//        // 파일 저장 경로 설정
//        Path path = (Path) Paths.get(uploadDir, loggedInUserId + "_" + profilePicture.getOriginalFilename());
//        File file = new File(path.toString());
//
//        // 디렉토리가 존재하지 않으면 생성
//        if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
//        
//        // 파일 저장
//        profilePicture.transferTo(file);
//
//        // 파일 경로 저장 (DB에 상대 경로 저장)
//        user.setProfile_image(loggedInUserId + "_" + profilePicture.getOriginalFilename());
//        userRepository.save(user);
//    }

    public UserInfoDTO getMemberById1(String uid) {
        return userRepository.findById(uid)
                .map(user -> new UserInfoDTO(
                    user.getUserid(), 
                    user.getUname()
                )).orElse(null); 
    }


}
