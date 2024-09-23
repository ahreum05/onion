package com.example.onion.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.onion.dto.JobboardDTO;
import com.example.onion.dto.MenuitemsDTO;
import com.example.onion.dto.SaleDTO;
import com.example.onion.dto.SaleboardDTO;
import com.example.onion.entity.Storecategory;
import com.example.onion.entity.Jobboard;
import com.example.onion.entity.Menuitems;
import com.example.onion.entity.Saleboard;
import com.example.onion.entity.Saleuser;
import com.example.onion.repository.StoreCategoryRepository;
import com.example.onion.repository.JobBoardRepository;
import com.example.onion.repository.MenuRepository;
import com.example.onion.repository.SaleRepository;
import com.example.onion.repository.SaleboardRepository;

@Repository
public class SaleDAO {

	@Autowired
	SaleRepository sr;

	@Autowired
	StoreCategoryRepository fr;

	@Autowired
	MenuRepository mr;

	@Autowired
	SaleboardRepository sbr;

	@Autowired
	JobBoardRepository jbr;

	// 로그인 확인
	public Saleuser loginCheck(String id, String pwd, String email) {
		Saleuser saleUser = sr.findBySaleIdAndSalePwdAndSaleEmail(id, pwd, email);
		return saleUser;
	}

	// 판매자 정보 저장하기
	public int register(SaleDTO dto) {
		int result = 0;
		Saleuser user = dto.toEntity();
		if (user != null) {
			sr.save(user);
			result = 1;
		}
		return result;
	}

	// 카테고리 FCEnking에 맞는 중분류 카테 불러오기
	public List<Storecategory> StorecateBySCname(String SCname) {
		List<Storecategory> list = fr.findBySCname(SCname);
		return list;
	}

	// 모든 카테 불러오기
	public List<Storecategory> AllStoreCategory() {
		List<Storecategory> categories = fr.findByAllStoreCategory();
		return categories;
	}

	// 판매자 정보 1개 불러오기 (아이디)
	public Saleuser SaleInfo(String id) {
		return sr.findById(id).orElse(null);
	}

	// 판매자 정보 1개 불러오기(가게 번호)
	public Saleuser SaleInfoStoreName(String Saleid) {
		Saleuser user = sr.findById(Saleid).orElse(null);
		return user;
	}



	// 판매자 정보 수정하기
	public int SaleModify(Saleuser saleuser) {
		int result = 0;
		Saleuser savedUser = sr.save(saleuser);
		if (savedUser != null) {
			result = 1;
		}
		return result;

	}

	// 메뉴 등록
	public int MenuInput(MenuitemsDTO dto) {
		int result = 0;
		Menuitems menu = dto.toEntity();
		if (menu != null) {
			mr.save(menu);
			result = 1;
		}
		return result;
	}

	// 메뉴 리스트 불러오기
	public List<Menuitems> menulist(String id) {
		List<Menuitems> menulist = mr.findByMIid(id);
		return menulist;
	}

	// 메뉴 1개 불러오기
	public Menuitems menuinfo(int seq) {
		return mr.findById(seq).orElse(null);
	}

	// 메뉴 수정하기
	public int MenuModify(Menuitems menu) {
		int result = 0;

		Menuitems menuitems = mr.save(menu);
		if (menuitems != null) {
			result = 1;
		}

		return result;
	}

	// 메뉴 평균가 출력 (게시중인)
	public int menuAvgPrice(String MIid) {
		Integer avg = mr.findByMIpriceAVG(MIid); // Integer로 수정하여 null을 처리할 수 있도록 함
		return (avg != null) ? avg : 0; // avg가 null일 경우 0 반환
	}

	// 메뉴 갯수
	public int menuCount(String id) {
		return mr.findByCount(id);
	}

	// 판매자 게시판 저장하기
	public int saveBoard(SaleboardDTO dto) {
		int result =0;
		Saleboard board = dto.toEntity();
		if(board!=null) {
			sbr.save(board);
			result = 1;
		}
		return result;
	}

	// 판매자 게시판 수정(이벤트/소식 등)
	public int modifyBoard(Saleboard board) {
		int result = 0;

		Saleboard board1 = sbr.save(board);
		if (board1 != null) {
			result = 1;
		}
		return result;
	}

	// 판매자 게시판 리스트 불러오기
	public List<Saleboard> saleboardlist(String id) {
		return sbr.findBySBIdList(id);
	}

	// 판매자 게시판 1개 불러오기
	public Saleboard boardinfo(int seq) {
		return sbr.findById(seq).orElse(null);
	}

	// 판매자 게시글 수
	public int boardCount(String id) {
		return sbr.findByCount(id);
	}

	// 판매자 게시판 삭제
	public int boardDelete(int seq) {
		int result = 0;
		Saleboard saleboard = sbr.findById(seq).orElse(null);

		if (saleboard != null) {
			sbr.delete(saleboard);
			result = 1;
		}

		return result;
	}



	// 판매자 전체 리스트
	public List<Saleuser> salerMainAllList() {
		return sr.findByAllList();
	}

	public int delete(String id) {
		int result = 0;
		Saleuser saleuser = sr.findById(id).orElse(null);
		if (saleuser != null) {
			sr.delete(saleuser);
			result = 1;
		}
		return result;
	}

	// 아이디 존재 확인
	public boolean checkId(String Saleid) {
		return sr.existsById(Saleid);
	}

	// 알바공고 저장
	public int savejobPost(JobboardDTO jobBoardPostDTO) {

		Jobboard jobboard = jobBoardPostDTO.toEntity();
		int result = 0;
		if (jobboard != null) {
			jbr.save(jobboard);
			result = 1;
		}
		// 성공 시 1 반환
		return result;
	}

	// 알바 공고 수정
	public int modifyjobPost(Jobboard jobBoardPost) {

		int result = 0;
		Jobboard jobboard = jbr.save(jobBoardPost);
		if (jobboard != null) {
			result = 1;
		}

		// 성공 시 1 반환
		return result;
	}

	// 알바 공고 삭제
	public int jobDelete(int seq) {
		int result = 0;
		Jobboard jobBoardPost = jbr.findById(seq).orElse(null);
		if (jobBoardPost != null) {
			jbr.delete(jobBoardPost);
			result = 1;
		}
		return result;
	}

	// 알바 공고 전체 리스트
	public List<Jobboard> jobAllList(String id) {
		return jbr.findByAllJobboard(id);
	}

	// 알바 공고 1개보기
	public Jobboard jobinfo(int seq) {
		return jbr.findById(seq).orElse(null);
	}
	
	// 알바 공고 전체 보기 
	public List<Jobboard> alljobinfo (){
		return jbr.findByAllJob();
	}

	public List<Jobboard> allJobWithSaleuserInfo() {
		// TODO Auto-generated method stub
        return jbr.findAllJobWithSaleuser();  // Jobboard와 연관된 Saleuser 정보도 함께 가져옴
	}
	
	public Jobboard JobWithSaleuserInfo(int seq) {
	
		return jbr.findByJobWithSaleuser(seq);
	}
}
