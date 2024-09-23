package com.example.onion.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    // 생성자를 통해 의존성 주입
    public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String combinedUsername = authentication.getName();  // "USER:username" 또는 "MANAGER:username"
        String rawPassword = (String) authentication.getCredentials();  // 입력된 비밀번호

        // "USER:" 또는 "MANAGER:" 접두사 제거
        String[] parts = combinedUsername.split(":");
        if (parts.length != 2) {
            throw new UsernameNotFoundException("잘못된 사용자 입력 형식입니다.");
        }

        String userType = parts[0];  // "USER" 또는 "MANAGER"
        String username = parts[1];  // 실제 아이디

        // 사용자 정보를 UserDetailsService에서 가져옴
        UserDetails userDetails = userDetailsService.loadUserByUsername(combinedUsername);
        String encodedPassword = userDetails.getPassword();  // DB에서 가져온 암호화된 비밀번호

        // 디버깅용 로그
        System.out.println("가져올 아이디: " + username);
        System.out.println("입력된 비밀번호: " + rawPassword);
        System.out.println("DB에서 조회된 암호화된 비밀번호: " + encodedPassword);

        // 비밀번호 일치 확인
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        System.out.println("같은가: " + matches);  // 결과를 출력

        if (matches) {
            // 비밀번호가 일치하면 인증 토큰 생성, 접두사 없는 username을 사용
            return new UsernamePasswordAuthenticationToken(username, rawPassword, userDetails.getAuthorities());
        } else {
            System.out.println("비밀번호 불일치: 인증 실패");
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
