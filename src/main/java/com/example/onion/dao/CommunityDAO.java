package com.example.onion.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.onion.dto.CommunityDTO;
import com.example.onion.entity.Boardinfo;
import com.example.onion.entity.Community;
import com.example.onion.repository.CommunityRepository;

@Repository
public class CommunityDAO {

	@Autowired
	CommunityRepository communityRepository;

	// 목록
	public List<Community> communityList(int startNum, int endNum) {
		return communityRepository.findByStartnumAndEndnum(startNum, endNum);
	}

	// 전체 데이터 개수
	public int getCount() {
		return (int) communityRepository.count();
	}

	// 글쓰기
	public boolean communityWrite(CommunityDTO dto) {
		Community community = dto.toEntity();
		return communityRepository.save(community) != null;
	}

	// 조회수 증가시키기
	public Community updateHit(int cseq) {
		// 1. 기존 데이터 가져오기
		Community community = communityRepository.findById(cseq).orElse(null);

		if (community != null) {
			// 2. 데이터 수정
			community.setChit(community.getChit() + 1);
			// 3. 저장
			return communityRepository.save(community);
		} else {
			return null;
		}
	}

	// 상세 보기
	public Community communityView(int cseq) {
		return communityRepository.findById(cseq).orElse(null);
	}

	// 삭제하기
	public boolean communityDelete(int cseq) {
		// 1. 삭제할 대상 가져오기
		Community community = communityRepository.findById(cseq).orElse(null);

		if (community != null) {
			// 2. 삭제하기
			communityRepository.delete(community);
		}

		// 3. 삭제확인
		return !communityRepository.existsById(cseq);
	}

	// 전체 데이터 개수
	public int getTotalA() {
		return (int) communityRepository.count();
	}

	// 좋아요 수 증가시키기
	public Community updateLikes(int cseq) {
		Community community = communityRepository.findById(cseq).orElse(null);
		if (community != null) {
			community.setClike(community.getClike() + 1); // 좋아요 수 증가
			return communityRepository.save(community);
		} else {
			return null;
		}
	}

	// 수정하기
	public boolean communityUpdate(CommunityDTO dto) {
		// 1. 기존 데이터 가져오기
		Community community = communityRepository.findById(dto.getCseq()).orElse(null);

		if (community != null) {
			// 2. 수정
			community.setCsub(dto.getCsub());
			community.setCcon(dto.getCcon());
			if (dto.getCimg() != null && !dto.getCimg().isEmpty()) {
				community.setCimg(dto.getCimg());
			}
			// 저장
			Community updatedCommunity = communityRepository.save(community);
			return updatedCommunity != null;
		}
		return false;
	}

	public List<Community> communityList(int startNum, int endNum, String search) {
		if (search == null || search.trim().isEmpty()) {
			return communityRepository.findByStartnumAndEndnum(startNum, endNum);
		} else {
			return communityRepository.findByStartnumAndEndnumAndSearch(startNum, endNum, search);
		}
	}

	// hit이 많은 수 대로 나열
	public List<Community> communityListByhit(int startNum, int endNum) {
		return communityRepository.findByChit(startNum, endNum);

	}


	public List<Community> communityListByHit(int startNum, int endNum, String search) {
		if (search == null || search.trim().isEmpty()) {
			return communityRepository.findByHit(startNum, endNum);
		} else {
			return communityRepository.findByHitAndSearch(startNum, endNum, search);
		}
	}

	public List<Community> communityListByLike(int startNum, int endNum, String search) {
		if (search == null || search.trim().isEmpty()) {
			return communityRepository.findByLike(startNum, endNum);
		} else {
			return communityRepository.findByLikeAndSearch(startNum, endNum, search);
		}
	}
	
	public Integer communitycount(String id) {
		Integer count = communityRepository.communitycount(id);
		if (count == null) {
			count = 0;
		}
		return count;
	}

	public List<Community> communityList(String id){
		List<Community> list = communityRepository.findBycid(id);
		return list;
	}

}
