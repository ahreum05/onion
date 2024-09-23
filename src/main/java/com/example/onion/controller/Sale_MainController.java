package com.example.onion.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.onion.dto.UserInfoDTO;

import com.example.onion.entity.Jobboard;
import com.example.onion.entity.Menuitems;
import com.example.onion.entity.Saleboard;
import com.example.onion.entity.Saleuser;
import com.example.onion.service.SaleService;
import com.example.onion.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class Sale_MainController {

	@Autowired
	private SaleService ss;

	@Autowired
	private UserService userService;

	@Value("${project.upload.path}")
	private String uploadpath;

	@GetMapping("/business/job")
	public String job(Model model, HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = "anonymous";
		if (authentication != null && authentication.isAuthenticated()
				&& !"anonymousUser".equals(authentication.getPrincipal())) {
			// 인증된 사용자의 경우 이름을 가져옴
			username = authentication.getName();
			UserInfoDTO userInfo = userService.getMemberById1(username);
			String id = userInfo.getUserid();
			String name = userInfo.getUname();

			model.addAttribute("userid", id);
			model.addAttribute("username", name);
		}

	    List<Jobboard> joblist = ss.allJobWithSaleuserInfo();

	    for (Jobboard job : joblist) {
	        // 각 job의 saleuser에서 주소 가져오기
	        String fullAddress = job.getSaleuser().getSaleaddress();  
	        
	        // 주소에서 "구"까지 추출하기
	        int districtEndIndex = fullAddress.indexOf("구");
	        String district = fullAddress;  // 기본적으로 전체 주소
	        
	        if (districtEndIndex != -1) {
	            // "구"까지 추출하여 새로운 값 저장
	            district = fullAddress.substring(0, districtEndIndex + 1);
	        }

	        // 추출한 "구"까지의 주소를 saleuser에 설정
	        job.getSaleuser().setSaleaddress(district);
	    }
		model.addAttribute("joblist", joblist);

		return "sale_main/job";
	}

	@GetMapping("/business/JobInfo")
	public String jobinfo(Model model, HttpServletRequest request) {

		int seq = Integer.parseInt(request.getParameter("seq"));
		
		Jobboard board = ss.JobWithSaleuserInfo(seq);
		System.out.println("board : "+board);
		
		model.addAttribute("board",board);
		
		return "sale_main/JobInfo";
	}

	@GetMapping("/business/store")
	public String store(Model model, HttpServletRequest request) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = "anonymous";
		if (authentication != null && authentication.isAuthenticated()
				&& !"anonymousUser".equals(authentication.getPrincipal())) {
			// 인증된 사용자의 경우 이름을 가져옴
			username = authentication.getName();
			UserInfoDTO userInfo = userService.getMemberById1(username);
			String id = userInfo.getUserid();
			String name = userInfo.getUname();

			model.addAttribute("userid", id);
			model.addAttribute("username", name);
		}

		List<Saleuser> salerList = ss.salerMainAllList();
		System.out.println("salerList : " + salerList);
		// map에 가게id + 가게 평균가를 저장함
		Map<String, Integer> avgPricesMap = new HashMap<String, Integer>();

		// 가게별로 메뉴의 평균 가격 계산
		// 그래야지 가게별 평균 가격을 가져 올수 있게 됨
		for (Saleuser saleuser : salerList) {
			int avgprice = ss.menuAvgPrice(saleuser.getSaleid()); // 각 가게의 메뉴 평균 가격을 가져옴\
			avgPricesMap.put(saleuser.getSaleid(), avgprice); // Saleid를 키로, 평균 가격을 값으로 저장
		}

		model.addAttribute("salerList", salerList);
		model.addAttribute("avgPricesMap", avgPricesMap);

		return "sale_main/Store";
	}

	// 판매자 정보 보여주기
	@GetMapping("/business/salerinfo")
	public String salerinfo(Model model, HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = "anonymous";
		if (authentication != null && authentication.isAuthenticated()
				&& !"anonymousUser".equals(authentication.getPrincipal())) {
			// 인증된 사용자의 경우 이름을 가져옴
			username = authentication.getName();
			UserInfoDTO userInfo = userService.getMemberById1(username);
			String id = userInfo.getUserid();
			String name = userInfo.getUname();

			model.addAttribute("userid", id);
			model.addAttribute("username", name);
		}
		

		
		String Saleid = request.getParameter("Saleid");
		HttpSession session = request.getSession();
		session.setAttribute("Saleid",Saleid);
		
		// 판매자 정보
		Saleuser saleuser = ss.SaleInfo(Saleid);

		// 메뉴 리스트 정보
		List<Menuitems> menuitems = ss.menulist(Saleid);

		// 게시판 정보
		List<Saleboard> boards = ss.saleboardlist(Saleid);

		model.addAttribute("saleuser", saleuser);
		model.addAttribute("menuitems", menuitems);
		model.addAttribute("boards", boards);

		return "/sale_main/SalerInfo";
	}

	// 판매자 게시글 내용
	@GetMapping("/business/boardInfo")
	public String test(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String Saleid=(String) session.getAttribute("Saleid");
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		Saleboard board = ss.boardinfo(seq);
		System.out.println("board : "+board);
		String id = board.getSBimg();
		model.addAttribute("board", board);
		model.addAttribute("Saleid", Saleid);

		return "/sale_main/boardInfo";
	}


}
