package com.example.onion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class OnionApplicationTests {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Test
	void contextLoads() {
		

		// 회원가입 시 암호화 저장
		String rawPassword = "4521354123ㅣㅓㅏ";
		String encodedPassword = passwordEncoder.encode(rawPassword);
		System.out.println("Encoded Password: " + encodedPassword);

		// 로그인 시 암호화된 비밀번호 비교
		boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
		System.out.println("비밀번호 일치 여부: " + matches);
	}

}
