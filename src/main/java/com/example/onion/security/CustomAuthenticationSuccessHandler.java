package com.example.onion.security;

import java.io.IOException;

import jakarta.servlet.ServletException;  
import jakarta.servlet.http.HttpServletRequest;  
import jakarta.servlet.http.HttpServletResponse; 

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
   
   private final RequestCache requestCache = new HttpSessionRequestCache();  // 요청 캐시 사용
   
   @Override
   public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                       Authentication authentication) throws IOException, ServletException {
       // 매니저가 아니면 핸들러를 처리
       boolean isManager = authentication.getAuthorities().stream()
           .anyMatch(grantedAuthority -> "ROLE_MANAGER".equals(grantedAuthority.getAuthority()));
       
       if (isManager) {
           // 매니저의 경우 별도로 처리하지 않고 다른 곳에서 처리하게 하기
           response.sendRedirect("/manager/dashboard"); // 바로 매니저 대시보드로 리다이렉트
           return;
       }

       // 사용자가 로그인하기 전에 요청한 URL 가져오기
       DefaultSavedRequest savedRequest = (DefaultSavedRequest) requestCache.getRequest(request, response);

       if (savedRequest != null) {
           String targetUrl = savedRequest.getRedirectUrl();
           if (targetUrl.contains("/loginForm")) {
               targetUrl = null;
           }
           if (targetUrl != null) {
               System.out.println("Redirecting to saved URL: " + targetUrl);
               response.sendRedirect(targetUrl);
               return;
           }
       } else {
           // 일반 사용자 권한에 따라 페이지를 리다이렉트
           for (GrantedAuthority auth : authentication.getAuthorities()) {
               if ("ROLE_USER".equals(auth.getAuthority())) {
                   response.sendRedirect("/main/main"); // 사용자 페이지로 리다이렉트
                   return;
               }
           }
       }

       // 기본적으로 메인 페이지로 리다이렉트
       response.sendRedirect("/main/main");
   }
}
