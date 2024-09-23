package com.example.onion.service;

import com.example.onion.dto.ManagerDTO;
import com.example.onion.entity.Manager;
import com.example.onion.entity.Userinfo;
import com.example.onion.repository.ManagerRepository;
import com.example.onion.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    UserRepository userRepository;
    
    // 기본 매니저 계정 생성
    public void createDefaultManager() {
        if (!managerRepository.existsById("admin")) {
            Manager manager = new Manager();
            manager.setMid("admin");
            manager.setMpwd(passwordEncoder.encode("admin"));  // 기본 비밀번호
            manager.setMlogtime(null);
            manager.setRole("MANAGER");
            managerRepository.save(manager);
        }
    }
    
    // 매니저 생성
    public void registerManager(String mid, String mpwd, String role) {
        // 입력값 검증 (간단한 검증, null 체크)
        if (mid == null || mid.isEmpty() || mpwd == null || mpwd.isEmpty()) {
            throw new IllegalArgumentException("아이디와 비밀번호는 필수 입력 항목입니다.");
        }

        // 중복된 아이디 체크
        if (managerRepository.existsById(mid)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 매니저 생성
        Manager manager = new Manager();
        manager.setMid(mid);
        manager.setMpwd(passwordEncoder.encode(mpwd));
        manager.setMlogtime(LocalDateTime.now());
        manager.setRole(role);
        managerRepository.save(manager);
    }


    // 매니저 조회
    public ManagerDTO getManagerById(String mid) {
        Optional<Manager> manager = managerRepository.findById(mid);
        return manager.map(Manager::toDTO).orElse(null);
    }

    // 매니저 업데이트
    public boolean updateManager(ManagerDTO managerDto) {
        // 입력값 검증 (간단한 검증, null 체크)
        if (managerDto.getMid() == null || managerDto.getMid().isEmpty()) {
            throw new IllegalArgumentException("아이디는 필수 입력 항목입니다.");
        }

        // 매니저 존재 여부 확인
        Optional<Manager> managerOpt = managerRepository.findById(managerDto.getMid());
        if (managerOpt.isPresent()) {
            Manager manager = managerOpt.get();
            manager.setMlogtime(LocalDateTime.now()); // 최근 로그인 시간 갱신
            manager.setRole(managerDto.getRole());

            // 비밀번호 업데이트 처리 (비밀번호 변경 요청이 있을 경우)
            if (managerDto.getMpwd() != null && !managerDto.getMpwd().isEmpty()) {
                manager.setMpwd(passwordEncoder.encode(managerDto.getMpwd()));
            }

            managerRepository.save(manager);
            return true;
        }
        return false;
    }


    // 매니저 삭제
    public void deleteManager(String mid) {
        // 기본 관리자 계정 삭제 방지
        if ("admin".equals(mid)) {
            throw new IllegalArgumentException("기본 관리자 계정은 삭제할 수 없습니다.");
        }

        // 매니저 존재 여부 확인 후 삭제
        if (!managerRepository.existsById(mid)) {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        }

        managerRepository.deleteById(mid);
    }


    // 매니저 목록 조회
    public List<ManagerDTO> findAllManagers() {
        List<Manager> managers = managerRepository.findAll();
        return managers.stream()
                       .map(Manager::toDTO)  // 엔티티 -> DTO 변환
                       .collect(Collectors.toList());
    }
    
 
    
}
