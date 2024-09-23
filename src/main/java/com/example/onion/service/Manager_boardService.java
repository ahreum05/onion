package com.example.onion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onion.dao.Manager_boardDAO;
import com.example.onion.dto.Manager_boardDTO;
import com.example.onion.entity.Manager_board;
@Service
public class Manager_boardService {
    @Autowired
    Manager_boardDAO dao;
    
	public List<Manager_board> noticeventList(int startNum, int endNum) {
		return dao.noticeventList(startNum, endNum);
	}

	public int getTotalA() {
		return dao.getTotalA();
	}

	public boolean noticeventWrite(Manager_boardDTO dto) {
		return dao.noticeventWrite(dto);
	}

	public Manager_board noticeventView(int seq) {
		dao.updateHit(seq);
		return dao.noticeventView(seq);
	}


	public boolean updateNoticevent(Manager_boardDTO dto) {
		return dao.updatNoticevent(dto);
		
	}

	public boolean noticeventDelete(int MBseq) {
        // DAO를 사용하여 삭제 작업을 수행
        return dao.noticeventDelete(MBseq);
    }
}


