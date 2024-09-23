package com.example.onion.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.onion.dto.BannerDTO;
import com.example.onion.dto.BoardInfoDTO;
import com.example.onion.dto.BoardcategoryDTO;
import com.example.onion.dto.UserInfoDTO;
import com.example.onion.entity.Boardinfo;
import com.example.onion.entity.Boardcategory;
import com.example.onion.service.BannerService;
import com.example.onion.service.UserService;
import com.example.onion.service.boardInfoService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BoardInfoController {
	@Autowired
	boardInfoService service;

	@Autowired
	UserService userService;

	@Autowired
	BannerService bannerService;

	// application.properties 파일에 있는 project.puload.path 설정값을
	// 문자열 변수에 저장시킴
	@Value("${project.upload.path}")
	private String uploadpath;

	@GetMapping("/boardinfo/WriteForm")
	public String WriteForm(Model model) {
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
		List<Boardcategory> cate = service.boardcategory();
		model.addAttribute("cate", cate);
		return "boardInfo/WriteForm";
	}

	@PostMapping("/boardinfo/Write")
	public String boardInfoWrite(BoardInfoDTO dto, Model model, HttpServletRequest request,
			@RequestParam("img") MultipartFile uploadFile) {
		// 1. 데이터 처리
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = "anonymous";
		if (authentication != null && authentication.isAuthenticated()
				&& !"anonymousUser".equals(authentication.getPrincipal())) {
			// 인증된 사용자의 경우 이름을 가져옴
			username = authentication.getName();
			UserInfoDTO userInfo = userService.getMemberById1(username);
			String id = userInfo.getUserid();
			String name = userInfo.getUname();

			dto.setBid(id);
			dto.setBname(name);

			model.addAttribute("userid", id);
			model.addAttribute("username", name);
		}

		// 파일명 처리
		String fileName = uploadFile.getOriginalFilename();
		dto.setBimg(fileName);
		dto.setBlogtime(new Date());

		if (!fileName.equals("")) {
			File file = new File(uploadpath, fileName);
			try {
				// 파일 저장하기
				uploadFile.transferTo(file);

				dto.setBcategory(request.getParameter("Bcategory"));
				dto.setBsub(request.getParameter("Bsub"));
				dto.setBcon(request.getParameter("Bcon"));
				dto.setBprc(request.getParameter("Bprc"));
				dto.setBhit(0);
				dto.setBlike(0);
				dto.setBimg(fileName);
				dto.setBlogtime(new Date());

				boolean result = service.boardInfoWrite(dto);
				// 데이터 공유
				model.addAttribute("result", result);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// view 처리 파일
		return "boardinfo/Write";
	}

	@GetMapping("/boardinfo/boardInfoList")
	public String boardInfoList(Model model, HttpServletRequest request) {

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

		String cate = request.getParameter("cate");
		String search = request.getParameter("search");

		int pg = 1; // 기본 페이지는 1
		String pgParam = request.getParameter("pg");
		if (pgParam != null && !pgParam.equals("")) {
			pg = Integer.parseInt(pgParam);
			if (pg == 0) {
				pg = 1; // pg가 0인 경우 1로 변경
			}
		}

		int itemsPerPage = 30; // 페이지 당 게시물 수
		int endNum = pg * itemsPerPage;
		int startNum = endNum - (itemsPerPage - 1);

		// 데이터 가져오기
		List<Boardinfo> list = service.boardListBcategory(cate);

		model.addAttribute("list", list);
		model.addAttribute("cate", cate);
		model.addAttribute("search", search);

		// 총 글 수 조회
		int totalA = service.getTotalByTag(cate);

		// 페이징 처리
		int blockSize = 3; // 한 번에 표시할 페이지 번호 수
		int totalP = (totalA + itemsPerPage - 1) / itemsPerPage; // 총 페이지 수 계산
		int startPage = (pg - 1) / blockSize * blockSize + 1;
		int endPage = startPage + blockSize - 1;
		if (endPage > totalP) {
			endPage = totalP;
		}

		model.addAttribute("pg", pg);
		model.addAttribute("totalP", totalP);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("uploadpath", uploadpath);

		return "boardinfo/boardInfoList";

	}

	@GetMapping("/boardinfo/boardInfoView")
	public String boardInfoView(Model model, HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = "anonymous";
		if (authentication != null && authentication.isAuthenticated()
				&& !"anonymousUser".equals(authentication.getPrincipal())) {
			// 인증된 사용자의 경우 이름을 가져옴
			username = authentication.getName();
			UserInfoDTO userInfo = userService.getMemberById1(username);
			String id = userInfo.getUserid();
			String name = userInfo.getUname();

			model.addAttribute("id", id);
			model.addAttribute("username", name);
		}

		int bseq = Integer.parseInt(request.getParameter("Bseq") != null ? request.getParameter("Bseq") : "0");
		int pg = Integer.parseInt(request.getParameter("pg") != null ? request.getParameter("pg") : "1");

		// 데이터 가져오기
		Boardinfo boardInfo = service.boardInfoView(bseq);
		// 모델에 데이터 추가
		model.addAttribute("boardInfo", boardInfo);
		model.addAttribute("Bseq", bseq);
		model.addAttribute("pg", pg);
		model.addAttribute("uploadpath", uploadpath);

		return "boardinfo/boardInfoView";
	}

	// 좋아요 버튼을 클릭했을 때 요청을 처리
	@GetMapping("/boardinfo/boardLike")
	public String likeCommunity(@RequestParam("seq") int Bseq, @RequestParam("pg") int pg,
			RedirectAttributes redirectAttributes, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String uid = authentication.getName();

		UserInfoDTO userInfo = userService.getMemberById1(uid);

		String id = userInfo.getUserid();
		String name = userInfo.getUname();

		model.addAttribute("userid", id);
		model.addAttribute("username", name);
		// 좋아요 수 증가
		service.likeCommunity(Bseq);

		// 리다이렉트 시 현재 페이지 정보 유지
		redirectAttributes.addAttribute("pg", pg);

		// 상세 페이지로 리다이렉트
		return "redirect:/board/boardInfoView?seq=" + Bseq;
	}

	// 삭제하기
	@GetMapping("/boardinfo/boardInfoDelete")
	public String boardInfoDelete(Model model, HttpServletRequest request) {
		int Bseq = Integer.parseInt(request.getParameter("Bseq") != null ? request.getParameter("Bseq") : "0");
		int pg = Integer.parseInt(request.getParameter("pg") != null ? request.getParameter("pg") : "1");
		System.out.println("Bseq : "+Bseq);
		System.out.println("pg : "+pg);
		// 한줄데이터 불러오기
		boolean result = service.boardInfoDelete(Bseq);

		// 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("pg", pg);
		model.addAttribute("http", "boardInfoDelete");
		return "boardinfo/result";
	}

	// 수정하기 폼
	@GetMapping("/boardinfo/boardModifyForm")
	public String boardModifyForm(Model model, HttpServletRequest request) {
		System.out.println("test : ");

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

		List<Boardcategory> cate = service.boardcategory();
		model.addAttribute("cate", cate);
		int Bseq = Integer.parseInt(request.getParameter("Bseq") != null ? request.getParameter("Bseq") : "0");
		int pg = Integer.parseInt(request.getParameter("pg") != null ? request.getParameter("pg") : "1");

		Boardinfo boardInfo = service.boardInfoView(Bseq);
		System.out.println("boardInfo : "+boardInfo);
		// 데이터 공유
		model.addAttribute("boardInfo", boardInfo);
		model.addAttribute("pg", pg);
		model.addAttribute("Bseq", Bseq);
		return "boardinfo/ModifyForm";
	}

	@PostMapping("/boardinfo/modify")
	public String boardModify(BoardInfoDTO dto, Model model, HttpServletRequest request,
			@RequestParam("img") MultipartFile uploadFile) {
		// 1. 데이터 처리
		int Bseq = Integer.parseInt(request.getParameter("Bseq") != null ? request.getParameter("Bseq") : "0");
		int pg = Integer.parseInt(request.getParameter("pg") != null ? request.getParameter("pg") : "1");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String uid = authentication.getName();

		UserInfoDTO userInfo = userService.getMemberById1(uid);

		String id = userInfo.getUserid();
		String name = userInfo.getUname();

		// 파일명 처리
		String fileName = uploadFile.getOriginalFilename();
		dto.setBimg(fileName);
		dto.setBlogtime(new Date());

		if (!fileName.equals("")) {
			File file = new File(uploadpath, fileName);
			try {
				// 파일 저장하기
				uploadFile.transferTo(file);
				dto.setBimg(fileName);
				dto.setBlogtime(new Date());

				boolean result = service.boardInfoModify(dto);
				// 데이터 공유
				model.addAttribute("result", result);
				model.addAttribute("http", "modify");
				model.addAttribute("Bseq", Bseq);
				model.addAttribute("pg", pg);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// view 처리 파일
		return "boardinfo/result";
	}

}
