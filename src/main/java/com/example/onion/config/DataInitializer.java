package com.example.onion.config; // 혹은 적절한 패키지 경로

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import com.example.onion.service.ManagerService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    @Lazy
    private ManagerService managerService;

    @Override
    public void run(String... args) throws Exception {
        // 애플리케이션이 시작될 때 기본 매니저 계정 생성
        managerService.createDefaultManager();
    }
}
