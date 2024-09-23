package com.example.onion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.function.Supplier;

@Entity
@Table(name = "USERINFO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Userinfo {
    
    @Id
    private String userid;
    private String uname;
    private String upwd;
 //   private String urepwd;
    private String ugender;
    private String ujumin1;
    private String ujumin2;
    private String utel1;
    private String utel2;
    private String utel3;
    private String uaddr;
    private String uemail1;
//   @Column(nullable = false)
    private String uemail2;
    private LocalDateTime ulogtime;
    private char is_active = 'Y'; // 기본값 설정
    private Date last_login_time;
    private String profile_image;
    private String role;
	
    public LocalDateTime getUlogtime() {
        return ulogtime;
    }
    
    public void setUlogtime(LocalDateTime ulogtime) {
        this.ulogtime = ulogtime;
    }
    
 // UserDetails 인터페이스 구현 메서드
    
    

    // Entity -> DTO 변환 메서드
//    public UserInfoDTO toDTO() {
//        return new UserInfoDTO(userid, uname, upwd, 
//        		ugender, ujumin, utel1, utel2, utel3, 
//        		uaddr, uemail1, uemail2, profileImage, ulogtime, 
//        		isActive, lastLoginTime, profileImage, role);
//    }
}

