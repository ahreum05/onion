package com.example.onion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.onion.dto.BannerDTO;
import com.example.onion.entity.Boardinfo;
import com.example.onion.entity.Boardcategory;
import com.example.onion.entity.Community;
import com.example.onion.service.BannerService;
import com.example.onion.service.CommunityService;
import com.example.onion.service.MainService;
import com.example.onion.service.boardInfoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

	@Value("${project.upload.path}")
	private String uploadpath;

	@Autowired
	boardInfoService boardservice;
	
	@Autowired
	MainService service;
	
	@Autowired
	CommunityService communityService;

	@Autowired
	BannerService bannerService;
	
	@GetMapping("/")
	public String mainRedirect(Model model, HttpServletRequest request) {
		// 현재 인증된 사용자의 인증 정보 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// 인증되지 않은 사용자의 경우 anonymous로 처리
		String username = "anonymous"; // 기본값 설정
		if (authentication != null && authentication.isAuthenticated()
				&& !"anonymousUser".equals(authentication.getPrincipal())) {
			// 인증된 사용자의 경우 이름을 가져옴
			username = authentication.getName();
		}
    	List<BannerDTO> bannerList = bannerService.getAllBanners();
        model.addAttribute("bannerList", bannerList);
		return "main/main"; // 메인 페이지로 리다이렉트
	}

	@GetMapping("/main/main")
	public String mainList(Model model, HttpServletRequest request) {
		int pg = 1;
		if (request.getParameter("pg") != null && !request.getParameter("pg").equals("")) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}

		int itemsPerPage = 5;
		int endNum = pg * itemsPerPage;
		int startNum = endNum - (itemsPerPage - 1);

		// 각각의 카테고리별로 데이터를 가져옴
		/*List<Boardinfo> latestList = service.boardInfoListByLogtime(startNum, endNum);*/
		/*List<Boardinfo> allList = service.boardInfoList(startNum, endNum);*/
		List<Boardinfo> popularList = service.boardInfoListByhit(startNum, endNum);
	//	List<Boardinfo> jjimList = service.boardInfoListByjjim(startNum, endNum);		
		List<Community> clikeList = communityService.communityListByhit(startNum, endNum);

		// 전체 페이지 수 계산
		int totalA = service.getTotalA();
		int totalP = (totalA + (itemsPerPage - 1)) / itemsPerPage;

		int startPage = (pg - 1) / 3 * 3 + 1;
		int endPage = startPage + 2;
		if (endPage > totalP) {
			endPage = totalP;
		}

		// 현재 인증된 사용자의 인증 정보 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// 인증되지 않은 사용자의 경우 anonymous로 처리
		String username = "anonymous"; // 기본값 설정
		if (authentication != null && authentication.isAuthenticated()
				&& !"anonymousUser".equals(authentication.getPrincipal())) {
			// 인증된 사용자의 경우 이름을 가져옴
			username = authentication.getName();
		}
		List<Community> list = communityService.communityListByhit(startNum, endNum);
		System.out.println("list : "+list);
		//배너
    	List<BannerDTO> bannerList = bannerService.getAllBanners();
        model.addAttribute("bannerList", bannerList);
		
		// 모델에 데이터 추가
		model.addAttribute("list", list);
		model.addAttribute("clikeList", clikeList);
		model.addAttribute("popularList", popularList);
	//	model.addAttribute("jjimList", jjimList);	
		model.addAttribute("pg", pg);
		model.addAttribute("totalP", totalP);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("uploadpath", uploadpath);
		model.addAttribute("username", username);

		return "main/main"; // src/main/resources/templates/main/main.html 뷰 반환
	}
	
	
	// 중고거래 카테고리
	@GetMapping("/main/boardInfocategory")
	public String boardInfocategory( Model model, HttpServletRequest request) {
		
		List<Boardcategory> list = boardservice.boardcategory();
		
		System.out.println("list : "+list);
		//배너
		List<BannerDTO> bannerList = bannerService.getAllBanners();
	    model.addAttribute("bannerList", bannerList);
		
		model.addAttribute("list",list);
		return "boardinfo/boardInfocategory";
	}
	
	
	@GetMapping("/main/terms")
	public String terms() {
		return "footer/terms";
	}
	
	@GetMapping("/main/inquiry")
	public String inquiry() {
		return "footer/inquiry";
	}
	
	@GetMapping("/main/introduce")
	public String introduce() {
		return "footer/introduce";
	}
	
	@GetMapping("/main/information")
	public String information() {
		return "footer/information";
	}
}