package com.example.onion.config;

import com.example.onion.security.CustomAuthenticationProvider;
import com.example.onion.security.CustomAuthenticationSuccessHandler;
import com.example.onion.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService customUserDetailsService; // 필드 주입

	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler; // SuccessHandler 주입

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	public AuthenticationProvider customAuthenticationProvider() {
		// CustomAuthenticationProvider 사용
		return new CustomAuthenticationProvider(userDetailsService(), passwordEncoder());
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
		         .headers(headers -> headers.frameOptions().sameOrigin())
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/css/**", "/js/**", "/img/**", "/smarteditor/**").permitAll() // 정적 리소스 접근 허용
						.requestMatchers("/loginForm", "/register", "/member/registerForm", "/login", "/error", "/main",
								"/", "/main/**", "/sale", "/sale/**", "/sale/registerForm","sale/**", "sale", "storage/**", "/storage/**",
								"storage", "/storage", "/ws/chat", "ws/**", "/ws/chat/**","/business/**","/search/**",
								"/static/**", "/static","/boardinfo/boardInfoList",
								"/boardinfo/boardInfoView/**","/boardinfo/boardInfoView","/member/checkId")
						.permitAll()

						.requestMatchers(HttpMethod.DELETE, "/api/banners/**").hasRole("MANAGER") // DELETE 요청 허용
						.requestMatchers("/manager/**").hasRole("MANAGER").requestMatchers("/user/**").hasRole("USER")
						.anyRequest().authenticated())
				.formLogin(login -> login.loginPage("/loginForm").loginProcessingUrl("/login")
						.successHandler(customAuthenticationSuccessHandler).permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/main/main")
						.logoutSuccessUrl("/main/main") // 로그아웃 후 이동할 페이지 설정
						.invalidateHttpSession(true) // 세션 무효화
						.deleteCookies("JSESSIONID") // JSESSIONID 쿠키 삭제
						.permitAll())
				.csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
				.authenticationProvider(customAuthenticationProvider()) // CustomAuthenticationProvider 설정

				.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return customUserDetailsService;
	}

}
