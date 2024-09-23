package com.example.onion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onion.dao.BoardInfoDAO;
import com.example.onion.dto.BoardInfoDTO;
import com.example.onion.entity.Boardinfo;
import com.example.onion.entity.Boardcategory;

import jakarta.transaction.Transactional;

@Service
public class boardInfoService {
	@Autowired
	private BoardInfoDAO dao;


	// 데이터 저장
	public boolean boardInfoWrite(BoardInfoDTO dto) {
		return dao.boardInfoWrite(dto);
	}

	// 인덱싱 뷰
	public List<Boardinfo> boardInfoList(int startNum, int endNum) {
		return dao.boardList(startNum, endNum);
	}

	// 신규 글 수 순으로 나열 
	public List<Boardinfo> boardInfoListByLogtime(int startNum, int endNum) {
		return dao.boardInfoListByLogtime(startNum, endNum);
	}

	// 목록 (카테 별)
	 public List<Boardinfo> boardListBcategory (String Bcategory){
		 return dao.boardListBcategory(Bcategory);
	 }
	
	// 총 글 수 구하기
	public int getTotalA() {
		return dao.getCount();
	}

	// 뷰처리
	public Boardinfo boardInfoView(int Bseq) {
		dao.updateHit(Bseq);
		return dao.boardInfoView(Bseq); 
	}

	// 삭제하기
	public boolean boardInfoDelete(int Bseq) {
		return dao.boardInfoDelete(Bseq);
	}
	
	// 좋아요 수 증가
    @Transactional
    public Boardinfo likeCommunity(int Bseq) {
        return dao.updateLikes(Bseq);
    }
    public int getTotalByTag(String tag) {
        return dao.getTotalByTag(tag);
    }

	public boolean boardInfoModify(BoardInfoDTO dto) {
		return dao.boardInfoModify(dto);
	}
	
	// 카테고리 불러오기
	public List<Boardcategory> boardcategory(){
		return dao.boardcategory();
	}
	
	
	public Integer boardcount(String id) {
		return dao.boardcount(id);
	}

	public List<Boardinfo> boardList(String id){
		return dao.boardList(id);
	}
	public List<Boardinfo> boardinfoList(int startNum, int endNum, String search){
		return dao.boardinfoList(startNum, endNum, search);
	}
}
