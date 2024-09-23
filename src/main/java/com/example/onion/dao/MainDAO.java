package com.example.onion.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.onion.entity.Boardinfo;
import com.example.onion.repository.MainRepository;

@Repository
public class MainDAO {
	@Autowired
	MainRepository mainRepository;

	// 목록
	/*public List<Boardinfo> boardList(int startNum, int endNum) {
		return mainRepository.findByStartnumAndEndnum(startNum, endNum);
	}*/

/*	// logtime으로 정렬된 게시물 가져오기
	public List<Boardinfo> boardListByLogtime(int startNum, int endNum) {
		return mainRepository.findByLogtime(startNum, endNum);
	}*/

	/*// bhit으로 정렬된 게시물 가져오기
	public List<Boardinfo> boardListByBhit(int startNum, int endNum) {
		return mainRepository.findByBhit(startNum, endNum);
	}*/

	/*// blike으로 정렬된 게시물 가져오기
	public List<Boardinfo> boardListByBlike(int startNum, int endNum) {
		return mainRepository.findByBlike(startNum, endNum);
	}*/

	public int getTotalA() {
		return (int) mainRepository.count();
	}

	// hit이 많은 수 대로 나열
	public List<Boardinfo> boardInfoListByhit(int startNum, int endNum) {
		return mainRepository.findByBhit(startNum, endNum);

	}

	// 찜이 많은 수대로 나열
	public List<Boardinfo> boardInfoListByjjim(int startNum, int endNum) {
		return mainRepository.findByBjjim(startNum, endNum);

	}

}
