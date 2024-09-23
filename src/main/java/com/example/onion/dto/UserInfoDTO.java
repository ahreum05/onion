package com.example.onion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

import com.example.onion.entity.Userinfo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private String userid;
    private String uname;
    private String upwd;
  //  private String urepwd; // 비밀번호 확인 필드
    private String ugender;
    private String ujumin1;
    private String ujumin2;
    private String utel1;
    private String utel2;
    private String utel3;
    private String uaddr;
    private String uemail1;
    private String uemail2;
    private LocalDateTime ulogtime;
    private char is_active = 'Y'; // 기본값 설정
    private Date last_login_time;
    private String profile_image;
    private String role;

    public UserInfoDTO(String userid, String uname) {
        this.userid = userid;
        this.uname = uname;
    }
    
    // DTO -> Entity 변환 메서드
    public Userinfo toEntity() {
        return new Userinfo(userid, uname, upwd, 
        		ugender, ujumin1, ujumin2, utel1, 
        		utel2, utel3, uaddr, uemail1, uemail2, 
        		ulogtime, is_active, last_login_time, 
        		profile_image, role);
    }
}
