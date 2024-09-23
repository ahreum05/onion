package com.example.onion.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.onion.entity.Userinfo;
import com.example.onion.repository.ManagerRepository;
import com.example.onion.repository.UserRepository;

@Repository
public class ManagerDAO {
	@Autowired
	ManagerRepository managerRepository;
	
	@Autowired
    UserRepository userRepository;
	
	// 회원정보 리스트
	public List<Userinfo> findByStartnumAndEndnum(int startNum, int endNum) {
		return userRepository.findByStartnumAndEndnum(startNum, endNum);
	}
	
	// 총글개수
    public int getCount() {
    	return (int)managerRepository.count();
    }
    
    
}
