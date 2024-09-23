package com.example.onion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.onion.dao.SaleDAO;
import com.example.onion.dto.JobboardDTO;
import com.example.onion.dto.MenuitemsDTO;
import com.example.onion.dto.SaleDTO;
import com.example.onion.dto.SaleboardDTO;
import com.example.onion.entity.Storecategory;
import com.example.onion.entity.Jobboard;
import com.example.onion.entity.Menuitems;
import com.example.onion.entity.Saleboard;
import com.example.onion.entity.Saleuser;

@Service
public class SaleService {

	@Autowired
	SaleDAO dao;

	// private final JavaMailSender javaMailSender;
	private static final String snederEmail = "ideaday00@gmail.com";
	private static int number;

	public static void createNumber() {
		number = (int) (Math.random() * (90000)) + 100000;
	}

	// public Mi

	// 로그인 확인
	public Saleuser loginCheck(String id, String pwd, String email) {
		return dao.loginCheck(id, pwd, email);
	}

	// 판매자 정보 저장하기
	public int register(SaleDTO dto) {
		return dao.register(dto);
	}

	// 카테고리 FCEnking에 맞는 중분류 카테 불러오기
	public List<Storecategory> StorecateBySCname(String FCEnking) {
		return dao.StorecateBySCname(FCEnking);
	}

	// 카테고리 전체 출력
	public List<Storecategory> AllStoreCategory() {
		return dao.AllStoreCategory();
	}

	// 판매자 정보 1개 불러오기 (아이디)
	public Saleuser SaleInfo(String id) {
		return dao.SaleInfo(id);
	}

	// 판매자 정보 1개 불러오기(가게 번호)
	public Saleuser SaleInfoStoreName(String Saleid) {
		return dao.SaleInfoStoreName(Saleid);
	}


	
	// 판매자 정보 수정하기
	public int SaleModify(Saleuser saleuser) {
		return dao.SaleModify(saleuser);
	}

	// 메뉴 등록
	public int MenuInput(MenuitemsDTO dto) {

		return dao.MenuInput(dto);
	}

	// 메뉴 리스트 불러오기
	public List<Menuitems> menulist(String id) {
		return dao.menulist(id);
	}

	// 메뉴 1개 불러오기
	public Menuitems menuinfo(int seq) {
		return dao.menuinfo(seq);
	}

	// 메뉴 수정하기
	public int MenuModify(Menuitems menu) {
		return dao.MenuModify(menu);

	}
	// 메뉴 갯수 
	public int menuCount(String id) {
		return dao.menuCount( id);
	}

	// 메뉴 평균가 출력 (게시중인)
	public int menuAvgPrice(String MIid) {
		return dao.menuAvgPrice(MIid);
	}

	// 판매자 게시판 저장 (이벤트/소식 등)
	public int saveBoard(SaleboardDTO dto) {
		return dao.saveBoard(dto);
	}
	// 판매자 게시판 수정(이벤트/소식 등)
	public int modifyBoard(Saleboard dto) {
		return dao.modifyBoard(dto);
	}


	// 판매자 게시글 리스트
	public List<Saleboard> saleboardlist(String id) {
		return dao.saleboardlist(id);
	}

	// 판매자 게시글 리스트 1개 보기
	public Saleboard boardinfo(int seq) {
		return dao.boardinfo(seq);
	}
	
	// 판매자 게시글 수 
	public int boardCount(String id) {
		return dao.boardCount(id);
	}
	
	// 판매자 게시글 삭제 
	public int boardDelete(int seq) {
		return dao.boardDelete(seq);

	}

	// 판매자 유저 전체 리스트
	public List<Saleuser> salerMainAllList() {
		return dao.salerMainAllList();
	}

	// 판매자 탈퇴
	public int delete(String id) {
		return dao.delete(id);
	}
	
	// 아이디 중복 체크
	public boolean checkId(String Saleid) {
		return dao.checkId(Saleid);
	}
	
	// 알바공고 저장
	public int savejobPost(JobboardDTO jobBoardPostDTO) {
		return dao.savejobPost(jobBoardPostDTO);
	}
	

	// 알바 공고 수정

	public int modifyjobPost(Jobboard jobBoardPost) {
		// TODO Auto-generated method stub
		return dao.modifyjobPost(jobBoardPost);
	}

	// 알바 공고 삭제

	public int jobDelete(int seq) {
		// TODO Auto-generated method stub
		return dao.jobDelete(seq);
	}
	//알바공고 전체 리스트
	public List<Jobboard> jobAllList(String id) {
		return dao.jobAllList(id);
	}
	//알바공고 1개 보기
	public Jobboard jobinfo(int seq) {
		return dao.jobinfo(seq);
	}
	// 알바 공고 전체 보기 
	public List<Jobboard> alljobinfo (){
		return dao.alljobinfo();
	}
	
	  // 모든 Jobboard와 연관된 Saleuser 정보 가져오기
    public List<Jobboard> allJobWithSaleuserInfo() {
        return dao.allJobWithSaleuserInfo();  // Jobboard와 연관된 Saleuser 정보도 함께 가져옴
    }

	
	public Jobboard JobWithSaleuserInfo(int seq) {
	
		return dao.JobWithSaleuserInfo(seq);
	}
}