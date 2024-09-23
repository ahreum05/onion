package com.example.onion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onion.entity.Userinfo;
import com.example.onion.repository.UserRepository;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    // 모든 회원정보를 조회하는 메서드
    public List<Userinfo> getAllProfiles() {
        return userRepository.findAll();
    }

    // 회원정보를 업데이트하는 메서드
    public void updateProfile(Userinfo user) {
        userRepository.save(user);
    }

    // 특정 회원의 정보를 삭제하는 메서드
    public void deleteProfile(String userId) {
        userRepository.deleteById(userId);
    }

    // 특정 회원의 정보를 조회하는 메서드
    public Optional<Userinfo> getProfileById(String userId) {
        return userRepository.findById(userId);
    }
    
    // userid로 유저 정보 가져오기
    public Userinfo getUserById(String userid) {
        return userRepository.findById(userid).orElse(null);
    }

	public List<Userinfo> getUserList(int startNum, int endNum) {
		// TODO Auto-generated method stub
		return userRepository.findByStartnumAndEndnum(startNum, endNum);
	}

	public int count() {
		return (int) userRepository.count();
	}
    
    
}

