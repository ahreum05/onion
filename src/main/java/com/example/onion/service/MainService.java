package com.example.onion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onion.dao.MainDAO;
import com.example.onion.entity.Boardinfo;

@Service
public class MainService {
	@Autowired
	MainDAO dao;

	/*public List<Boardinfo> boardInfoList(int startNum, int endNum) {
		return dao.boardList(startNum, endNum);
	}

	public List<Boardinfo> boardInfoListByLogtime(int startNum, int endNum) {
		return dao.boardListByLogtime(startNum, endNum);
	}

	public List<Boardinfo> boardInfoListByBhit(int startNum, int endNum) {
		return dao.boardListByBhit(startNum, endNum);
	}

	public List<Boardinfo> boardInfoListByBlike(int startNum, int endNum) {
		return dao.boardListByBlike(startNum, endNum);
	}*/

	public int getTotalA() {
		return dao.getTotalA();
	}

	// 찜 수 순 으로 나열
	public List<Boardinfo> boardInfoListByjjim(int startNum, int endNum) {
		return dao.boardInfoListByjjim(startNum, endNum);
	}

	// 많이 본 순 으로 나열
	public List<Boardinfo> boardInfoListByhit(int startNum, int endNum) {
		return dao.boardInfoListByhit(startNum, endNum);
	}

}
