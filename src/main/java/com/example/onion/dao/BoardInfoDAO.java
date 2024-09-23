package com.example.onion.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.onion.dto.BoardInfoDTO;
import com.example.onion.entity.Boardinfo;
import com.example.onion.entity.Boardcategory;
import com.example.onion.repository.BoardInfoRepository;
import com.example.onion.repository.BoardcategoryRepository;

@Repository
public class BoardInfoDAO {

	@Autowired
	BoardInfoRepository boardInfoRepository;

	@Autowired
	BoardcategoryRepository bcr;
	
	// 목록
	public List<Boardinfo> boardList(int startNum, int endNum) {
		return boardInfoRepository.findByStartnumAndEndnum(startNum, endNum);
	}

	public List<Boardinfo> boardInfoListByLogtime(int startNum, int endNum) {
		return boardInfoRepository.findByLogtime(startNum, endNum);
	}

	// 목록 (카테 별)
	public List<Boardinfo> boardListBcategory(String Bcategory) {
		return boardInfoRepository.findByBcategory(Bcategory);
	}

	// 전체 데이터 개수
	public int getCount() {
		return (int) boardInfoRepository.count();
	}

	// 글쓰기
	public boolean boardInfoWrite(BoardInfoDTO dto) {
		Boardinfo boardInfo = dto.toEntity();
		return boardInfoRepository.save(boardInfo) != null;
	}

	// 상세 보기
	public Boardinfo boardInfoView(int Bseq) {
		return boardInfoRepository.findById(Bseq).orElse(null);
	}

	// 삭제하기
	public boolean boardInfoDelete(int Bseq) {
		// 1. 삭제할 대상 가져오기
		Boardinfo boardInfo = boardInfoRepository.findById(Bseq).orElse(null);

		if (boardInfo != null) {
			// 2. 삭제하기
			boardInfoRepository.delete(boardInfo);
		}
		// 3. 삭제확인
		return !boardInfoRepository.existsById(Bseq);
	}

	// 수정하기
	public boolean boardInfoUpdate(BoardInfoDTO dto) {
		// 1. 기존 데이터 가져오기
		Boardinfo boardInfo = boardInfoRepository.findById(dto.getBseq()).orElse(null);

		if (boardInfo != null) {
			// 2. 수정
			boardInfo.setBsub(dto.getBsub());
			boardInfo.setBcon(dto.getBcon());
			// 저장
			Boardinfo boardInfo_result = boardInfoRepository.save(boardInfo);
			boolean result = false;
			if (boardInfo_result != null)
				result = true;
			return result;
		}
		return false;
	}

	// 조회수 증가
	public Boardinfo updateHit(int Bseq) {
		Boardinfo boardInfo = boardInfoRepository.findById(Bseq).orElse(null);

		if (boardInfo != null) {
			boardInfo.setBhit(boardInfo.getBhit() + 1);
			return boardInfoRepository.save(boardInfo);
		} else {
			return null;
		}
	}

	// 좋아요 수 증가시키기
	public Boardinfo updateLikes(int Bseq) {
		Boardinfo boardInfo = boardInfoRepository.findById(Bseq).orElse(null);
		if (boardInfo != null) {
			boardInfo.setBlike(boardInfo.getBlike() + 1); // 좋아요 수 증가
			return boardInfoRepository.save(boardInfo);
		} else {
			return null;
		}
	}

	
	public int getTotalByTag(String tag) {
		return boardInfoRepository.countByTag(tag);
	}

	// 수정하기
	public boolean boardInfoModify(BoardInfoDTO dto) {
		boolean result = false;
		Boardinfo boardInfo = dto.toEntity();
		if (boardInfo != null) {
			boardInfoRepository.save(boardInfo);
			result = true;
		}
		return result;
	}
	
	// 카테고리 불러오기
	public List<Boardcategory> boardcategory(){
		return bcr.findAllList();
	}
	
	public Integer boardcount(String id) {
		Integer count = boardInfoRepository.boardcount(id);
		if (count == null) {
			count = 0;
		}
		return count;
	}

	public List<Boardinfo> boardList(String id){
		List<Boardinfo> list = boardInfoRepository.findByBid(id);
		return list;
	}

	public List<Boardinfo> boardinfoList(int startNum, int endNum, String search){
		if (search == null || search.trim().isEmpty()) {
			return boardInfoRepository.findByStartnumAndEndnum(startNum, endNum);
		}else {
			return boardInfoRepository.findByStartnumAndEndnumAndSearch(startNum, endNum, search);
		}
	}

}
