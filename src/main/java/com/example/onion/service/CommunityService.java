package com.example.onion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onion.dao.CommunityDAO;
import com.example.onion.dto.CommunityDTO;
import com.example.onion.entity.Community;
import com.example.onion.repository.CommunityRepository;

import jakarta.transaction.Transactional;

@Service
public class CommunityService {
	@Autowired
	CommunityDAO dao;
	
	private final CommunityRepository communityRepository;

    @Autowired
    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

	public List<Community> communityList(int startNum, int endNum) {
		return dao.communityList(startNum, endNum);
	}

	public int getTotalA() {
		return dao.getTotalA();
	}

	public boolean communityWrite(CommunityDTO dto) {
		return dao.communityWrite(dto);
	}

	public Community communityView(int cseq) {
		dao.updateHit(cseq);
		return dao.communityView(cseq);
	}

	public boolean communityDelete(int cseq) {
		return dao.communityDelete(cseq);
	}


	 // 좋아요 수 증가
    @Transactional
    public Community likeCommunity(int cseq) {
        return dao.updateLikes(cseq);
    }
	
    @Transactional
    public boolean updateCommunity(CommunityDTO dto) {
        return dao.communityUpdate(dto);
    }
    
    public List<Community> communityListByhit(int startNum, int endNum) {
		return dao.communityListByhit(startNum, endNum);
	}
    
    public List<Community> communityList(int startNum, int endNum, String search) {
        return dao.communityList(startNum, endNum, search);
    }

    public List<Community> communityListByHit(int startNum, int endNum, String search) {
        return dao.communityListByHit(startNum, endNum, search);
    }

    public List<Community> communityListByLike(int startNum, int endNum, String search) {
        return dao.communityListByLike(startNum, endNum, search);
    }
    
	public Integer communitycount(String id) {
		return dao.communitycount(id);
	}

	public List<Community> communityList(String id){
		return dao.communityList(id);
	}
	
//	public List<Community> searchCommunities(String query) {
//        return communityRepository.findByCsubContainingIgnoreCase(query);
//    }

}
