package com.example.onion.service;

import com.example.onion.dto.UserInfoDTO;
import com.example.onion.entity.Manager;
import com.example.onion.entity.Userinfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.onion.repository.ManagerRepository;
import com.example.onion.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Autowired
    private ManagerRepository managerRepository;
    
    // 사용자 등록 메서드
    public void registerUser(UserInfoDTO dto) {
        // DTO에서 엔티티로 변환한 후
        Userinfo newUser = dto.toEntity();

        // 비밀번호를 암호화 후 저장 (중복 암호화 방지)
        newUser.setUpwd(passwordEncoder.encode(dto.getUpwd()));
        userRepository.save(newUser);
    }
    
    @Override
    public UserDetails loadUserByUsername(String combinedUsername) throws UsernameNotFoundException {
        // 사용자가 입력한 아이디를 ":"로 분리
        String[] parts = combinedUsername.split(":");
        
        if (parts.length != 2) {
            throw new UsernameNotFoundException("잘못된 사용자 입력 형식입니다."); // parts의 길이가 2가 아니면 예외 발생
        }

        String userType = parts[0]; // 접두사 (USER, MANAGER)
        String username = parts[1]; // 실제 아이디

        if ("USER".equalsIgnoreCase(userType)) {
            Userinfo user = userRepository.findByUserid(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserid())
                .password(user.getUpwd())
                .roles(user.getRole())
                .build();
        } else if ("MANAGER".equalsIgnoreCase(userType)) {
            Manager manager = managerRepository.findByMid(username)
                .orElseThrow(() -> new UsernameNotFoundException("Manager not found"));
            return org.springframework.security.core.userdetails.User
                .withUsername(manager.getMid())
                .password(manager.getMpwd())
                .roles(manager.getRole())
                .build();
        } else {
            throw new UsernameNotFoundException("Unknown user type");
        }
    }

}
