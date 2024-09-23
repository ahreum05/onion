package com.example.onion.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.onion.dto.JobboardDTO;
import com.example.onion.dto.MenuitemsDTO;
import com.example.onion.dto.SaleDTO;
import com.example.onion.dto.SaleboardDTO;
import com.example.onion.entity.Jobboard;
import com.example.onion.entity.Menuitems;
import com.example.onion.entity.Saleboard;
import com.example.onion.entity.Saleuser;
import com.example.onion.entity.Storecategory;
import com.example.onion.service.ChatRoomService;
import com.example.onion.service.SaleService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class SaleController {

	@Autowired
	private SaleService ss;

	@Autowired
	ChatRoomService crs;

	@Value("${project.upload.path}")
	private String uploadpath;

	// 메일 처리
	@ResponseBody
	@PostMapping("/sale/mail")
	public String MailSend(String mail) {
		// int number = saleService.sendMail(mail);

		// String num = "" +number;
		return "";
	}

	// 로그인 폼 이동
	@GetMapping("/sale/loginForm")
	public String loginForm() {

		return "/sale/loginForm";
	}

	// 로그인 처리
	@PostMapping("/sale/login")
	public String login(Model model, HttpServletRequest request) {

		String Saleid = request.getParameter("Saleid");
		String Salepwd = request.getParameter("Salepwd");
		String Saleemail = request.getParameter("Saleemail");

		// 로그인 검증
		Saleuser saleuser = ss.loginCheck(Saleid, Salepwd, Saleemail);

		// saleuser가 null이 아닌지 확인
		if (saleuser != null) {
			// 가게 이름을 가져옴
			String storename = saleuser.getSalestorename();

			// session - 사용자 ID와 가게 이름을 저장
			HttpSession session = request.getSession();
			session.setAttribute("Sid", Saleid);
			session.setAttribute("Sname", storename);

			System.out.println("sid :");
			return "redirect:/sale/main"; // 로그인 성공 시 메인 페이지
		} else {
			model.addAttribute("html", "login");
			return "/sale/result";
		}
	}

	// 회원가입 폼 이동
	@GetMapping("/sale/registerForm")
	public String registerForm(Model model) {

		List<Storecategory> catelist = ss.AllStoreCategory();

		model.addAttribute("catelist", catelist);

		return "/sale/registerForm";
	}

	// 아이디 중복 체크
	@GetMapping("/sale/checkId")
	public String checkId(Model model, HttpServletRequest request) {
		String saleid = request.getParameter("Saleid");

		boolean result = ss.checkId(saleid);

		model.addAttribute("result", result);
		model.addAttribute("saleid", saleid);
		return "/sale/checkId";
	}

	// 회원가입 처리
	@PostMapping("/sale/register")
	public String register(SaleDTO dto, Model model, HttpServletRequest request,
			@RequestParam("Simg") MultipartFile uploadFile1, @RequestParam("Bimg") MultipartFile uploadFile2) {

		// 곂치지 않게하기 위해서 날짜 집어넣음
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentTime = sdf.format(new Date());
		String Saleaddress1 = request.getParameter("Saleaddress1");
		String Saleaddress2 = request.getParameter("Saleaddress2");
		String Saleaddress3 = request.getParameter("Saleaddress3");

		String address = Saleaddress1 + " " + Saleaddress2 + " " + Saleaddress3;

		// 이미지 명 처리하기
		String SImg = currentTime + "_" + uploadFile1.getOriginalFilename();
		String BImg = currentTime + "_" + uploadFile2.getOriginalFilename();

		if (!(SImg.equals("") && BImg.equals(""))) {
			File file1 = new File(uploadpath, SImg);
			File file2 = new File(uploadpath, BImg);
			try {
				// 이미지 저장하기
				uploadFile1.transferTo(file1);
				uploadFile2.transferTo(file2);
				dto.setSaleimage(SImg);
				dto.setSalebusinessimg(BImg);
				dto.setSaleaddress(address);

				dto.setSalelogtime(new Date());

				int result = ss.register(dto);
				System.out.println("dto :" + dto);
				System.out.println("result L" + result);
				model.addAttribute("result", result);
				model.addAttribute("html", "register");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "/sale/result"; // 회원가입 성공 시 로그인 페이지로 이동

	}

	// 메인화면
	@GetMapping("/sale/main")
	public String main(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("Sid");

		model.addAttribute("id", id);

		return "/sale/main";
	}

	// 결과화면
	@GetMapping("/sale/result")
	public String result() {
		return "/sale/result";
	}

	// 대쉬보드
	@GetMapping("/sale/dashboard")
	public String dashboard(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("Sid");

		Saleuser saleuser = ss.SaleInfo(id);

		// 메뉴 수
		int menucount = ss.menuCount(id);

		// 게시판 수
		int boardcount = ss.boardCount(id);

		// 총 채팅 수
		int chatcount = crs.chatcount(id);

		// 오늘의 채팅수
		int chattodaycount = crs.chatdatecount(id);

		model.addAttribute("menucount", menucount);
		model.addAttribute("boardcount", boardcount);
		model.addAttribute("chatcount", chatcount);
		model.addAttribute("chattodaycount", chattodaycount);

		return "/sale/dashboard";
	}

	// 판매자 정보 확인
	@GetMapping("/sale/SaleInfo")
	public String SaleInfo(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("Sid");

		Saleuser saleuser = ss.SaleInfo(id);

		// 저장된 이미지 파일 경로
		String Bimg = "/storage/" + saleuser.getSalebusinessimg();
		String Simg = "/storage/" + saleuser.getSaleimage();

		model.addAttribute("saleuser", saleuser);
		model.addAttribute("Bimg", Bimg); // 사업자 등록증 이미지 URL
		model.addAttribute("Simg", Simg); // 가게 이미지 URL

		return "/sale/SaleInfo";
	}

	// 판매자 정보 수정 폼
	@PostMapping("/sale/SaleInfoModifyForm")
	public String SaleInfoModifyForm(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("Sid");

		Saleuser saleuser = ss.SaleInfo(id);

		// 음식 카테고리
		List<Storecategory> catelist = ss.AllStoreCategory();

		model.addAttribute("catelist", catelist);
		model.addAttribute("catelist", catelist);
		model.addAttribute("saleuser", saleuser);

		return "/sale/SaleInfoModifyForm";
	}

	// 판매자 정보 수정
	@PostMapping("/sale/SaleInfoModify")
	public String SaleInfoModify(SaleDTO dto, Model model, HttpServletRequest request,
			@RequestParam("Simg") MultipartFile uploadFile1, @RequestParam("Bimg") MultipartFile uploadFile2) {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("Sid");

		// 곂치지 않게하기 위해서 날짜 집어넣음
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentTime = sdf.format(new Date());

		// 이미지 명 처리하기
		String SImg = currentTime + "_" + uploadFile1.getOriginalFilename();
		String BImg = currentTime + "_" + uploadFile2.getOriginalFilename();

		String Saleaddress1 = request.getParameter("Saleaddress1");
		String Saleaddress2 = request.getParameter("Saleaddress2");
		String Saleaddress3 = request.getParameter("Saleaddress3");

		String address = Saleaddress1 + " " + Saleaddress2 + " " + Saleaddress3;
		if (!(SImg.equals("") && BImg.equals(""))) {
			File file1 = new File(uploadpath, SImg);
			File file2 = new File(uploadpath, BImg);
			try {
				// 이미지 저장하기
				uploadFile1.transferTo(file1);
				uploadFile2.transferTo(file2);

				Saleuser saleuser = ss.SaleInfo(id);
				if (saleuser != null) {
					saleuser.setSalepwd(request.getParameter("Salepwd"));
					saleuser.setSaletel(request.getParameter("Saletel"));
					saleuser.setSaleemail(request.getParameter("Saleemail"));

					saleuser.setSalestorename(request.getParameter("Salestorename"));
					saleuser.setSaleurl(request.getParameter("Saleurl"));
					saleuser.setSalestoretel(request.getParameter("Salestoretel"));
					saleuser.setSalecategory(request.getParameter("Salecategory"));
					saleuser.setSaleopen(request.getParameter("Saleopen"));
					saleuser.setSaleclose(request.getParameter("Saleclose"));
					saleuser.setSaleimage(SImg);

					saleuser.setSaleceo(request.getParameter("Saleceo"));
					saleuser.setSalecompanyname(request.getParameter("Salecompanyname"));
					saleuser.setSalebusinessnum(request.getParameter("Salebusinessnum"));
					saleuser.setSalebusinessimg(BImg);

					int result = ss.SaleModify(saleuser);
					dto.setSaleaddress(address);

					model.addAttribute("result", result);
					model.addAttribute("html", "SaleInfoModify");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "/sale/result";
	}

	// 판매자 로그아웃 폼
	@GetMapping("/sale/logout")
	public String logout(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("Sid");
		String Name = (String) session.getAttribute("Sname");

		session.removeAttribute(id);
		session.removeAttribute(Name);

		session.invalidate();

		return "redirect:/sale/loginForm";
	}

	// 판매자 삭제 폼
	@GetMapping("/sale/delete")
	public String SaleDelete(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("Sid");

		int result = ss.delete(id);

		model.addAttribute("result", result);
		model.addAttribute("html", "delete");

		return "/sale/result";
	}

	// 판매자 음식 메뉴 등록폼
	@GetMapping("/sale/menuInputForm")
	public String MenuInputForm() {

		return "/sale/MenuInputForm";
	}

	// 판매자 음식 메뉴 등록 확인
	@PostMapping("/sale/menuInput")
	public String MenuInput(MenuitemsDTO dto, Model model, HttpServletRequest request,
			@RequestParam("img") MultipartFile uploadfile) {

		// 곂치지 않게하기 위해서 날짜 집어넣음
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentTime = sdf.format(new Date());

		// 이미지
		String fileName = currentTime + "_" + uploadfile.getOriginalFilename();

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("Sid");

		// 체크박스 값 처리
		String isAvailableStr = request.getParameter("is_available");
		int isAvailable = (isAvailableStr != null) ? 1 : 0;

		if (!uploadfile.isEmpty()) {
			File file = new File(uploadpath, fileName);
			try {
				uploadfile.transferTo(file);

				dto.setMIid(id);
				dto.setMIprice(Integer.parseInt(request.getParameter("MIprice")));
				dto.setMIimg(fileName);
				dto.setMIlogtime(new Date());
				dto.setIs_available(isAvailable);
				dto.setMIhit(0);

				int result = ss.MenuInput(dto);

				model.addAttribute("result", result);
				model.addAttribute("html", "MenuInput");

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			model.addAttribute("error", "파일이 업로드되지 않았습니다.");
			return "/sale/error"; // 오류 페이지로 리다이렉트
		}

		return "/sale/result";
	}

	// 판매자 메뉴 리스트/
	@GetMapping("/sale/menulist")
	public String menulist(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();

		String id = (String) session.getAttribute("Sid");

		List<Menuitems> menulist = ss.menulist(id);
		// 저장된 이미지 파일 경로
		model.addAttribute("menulist", menulist);

		return "/sale/MenuList";
	}

	// 판매자 메뉴 1개 확인
	@GetMapping("/sale/menuinfo")
	public String menuInfo(Model model, HttpServletRequest request) {

		int seq = Integer.parseInt(request.getParameter("seq"));

		Menuitems menu = ss.menuinfo(seq);

		model.addAttribute("menu", menu);
		return "/sale/MenuInfo";
	}

	// 메뉴 수정 폼
	@PostMapping("/sale/menuinfomodifyForm")
	public String menuinfomodifyForm(Model model, HttpServletRequest request) {

		int seq = Integer.parseInt(request.getParameter("seq"));
		Menuitems menu = ss.menuinfo(seq);

		model.addAttribute("seq", seq);
		model.addAttribute("menu", menu);

		return "/sale/MenuInfoModifyForm";
	}

	// 메뉴 수정 처리
	@PostMapping("/sale/menuinfomodify")
	public String menuinfomodify(MenuitemsDTO dto, Model model, HttpServletRequest request,
			@RequestParam("img") MultipartFile uploadfile) {
		System.out.println("text");
		int seq = Integer.parseInt(request.getParameter("seq"));

		Menuitems menu = ss.menuinfo(seq);
		System.out.println("menu : "+menu);

		// 곂치지 않게하기 위해서 날짜 집어넣음
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentTime = sdf.format(new Date());

		// 이미지
		String fileName = currentTime + "_" + uploadfile.getOriginalFilename();

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("Sid");

		// 체크박스 값 처리
		String isAvailableStr = request.getParameter("is_available");
		int isAvailable = (isAvailableStr != null) ? 1 : 0;
		String name = request.getParameter("MIitemname");
		System.out.println("name= "+name);
		
		if (!uploadfile.isEmpty()) {
			File file = new File(uploadpath, fileName);
			try {
				uploadfile.transferTo(file);

				menu.setMIid(id);
				menu.setMIitemname(name);
				menu.setMIprice(Integer.parseInt(request.getParameter("MIprice")));
				menu.setMIimg(fileName);
				menu.setIs_available(isAvailable);

				int result = ss.MenuModify(menu);

				model.addAttribute("result", result);
				model.addAttribute("html", "MenuInputModify");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "/sale/result";
	}

	// 판매자 채용공고 폼
	@GetMapping("/sale/jobWriteForm")
	public String jobWriteForm() {

		return "/sale/JobWriteForm";
	}

	// 판매자 게시글 폼
	@GetMapping("/sale/boardwriteform")
	public String boardwriteform() {

		return "/sale/boardwriteform";
	}

	// 판매자 게시글
	@PostMapping("/sale/boardwrite")
	public String saveBoard(SaleboardDTO dto, Model model, HttpServletRequest request,
			@RequestParam("img") MultipartFile uploadfile) {

		// 시간 정보를 사용하여 파일명 생성
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		// 이미지 파일이 있을 때만 파일명 생성
		String fileName = currentTime + "_" + uploadfile.getOriginalFilename();
		String subject = request.getParameter("subject");
		String content = request.getParameter("editorTxt");

		// 아이디 불러오기
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("Sid");

		// 체크박스 값 처리
		String isAvailableStr = request.getParameter("is_available");
		int isAvailable = (isAvailableStr != null) ? 1 : 0;

		try {
			// 이미지 파일이 있을 경우 저장
			if (fileName != null) {
				File file = new File(uploadpath, fileName);
				uploadfile.transferTo(file); // 이미지 파일 저장
				dto.setSBimg(fileName); // DTO에 이미지 경로 설정

				// 게시글 정보 저장
				dto.setSBid(id);
				dto.setSBsubject(subject);
				dto.setSBcontent(content);
				dto.setSBlogtime(new Date());
				dto.setIs_available(isAvailable);

				int result = ss.saveBoard(dto);

				model.addAttribute("result", result);
				model.addAttribute("html", "boardwrite");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "파일 업로드 또는 저장 중 오류가 발생했습니다.");
			return "/sale/error"; // 에러 페이지로 이동
		}
		System.out.println("saleboardDTO : " + dto);

		return "/sale/result";
	}

	// 게시판 리스트
	@GetMapping("/sale/boardlist")
	public String boardlist(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();

		String id = (String) session.getAttribute("Sid");
		List<Saleboard> sblist = ss.saleboardlist(id);

		// 저장된 이미지 파일 경로
		model.addAttribute("sblist", sblist);

		return "sale/BoardList";
	}

	// 게시판 1개 확인
	@GetMapping("/sale/boardinfo")
	public String boardinfo(Model model, HttpServletRequest request) {
		int seq = Integer.parseInt(request.getParameter("seq"));
		Saleboard board = ss.boardinfo(seq);

		model.addAttribute("board", board);
		return "/sale/Boardinfo";
	}

	// 게시판 수정 폼
	@PostMapping("/sale/boardinfomodifyForm")
	public String boardinfomodifyForm(Model model, HttpServletRequest request) {

		int seq = Integer.parseInt(request.getParameter("seq"));
		Saleboard board = ss.boardinfo(seq);

		model.addAttribute("seq", seq);
		model.addAttribute("board", board);

		return "/sale/BoardInfoModifyForm";
	}

	// 게시판 수정 처리
	@PostMapping("/sale/boardmodify")
	public String boardinfomodify(SaleboardDTO dto, Model model, HttpServletRequest request,
			@RequestParam("img") MultipartFile uploadfile) {

		int seq = Integer.parseInt(request.getParameter("seq"));

		Saleboard board = ss.boardinfo(seq);
		System.out.println("board : " + board);
		// 시간 정보를 사용하여 파일명 생성
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		// 이미지 파일이 있을 때만 파일명 생성
		String fileName = currentTime + "_" + uploadfile.getOriginalFilename();
		String subject = request.getParameter("subject");
		String content = request.getParameter("editorTxt");

		// 아이디 불러오기
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("Sid");

		// 체크박스 값 처리
		String isAvailableStr = request.getParameter("is_available");
		int isAvailable = (isAvailableStr != null) ? 1 : 0;

		try {
			// 이미지 파일이 있을 경우 저장
			if (fileName != null) {
				File file = new File(uploadpath, fileName);
				uploadfile.transferTo(file); // 이미지 파일 저장
				board.setSBimg(fileName); // DTO에 이미지 경로 설정

				// 게시글 정보 저장
				board.setSBid(id);
				board.setSBsubject(subject);
				board.setSBcontent(content);
				board.setSBlogtime(new Date());
				board.setIs_available(isAvailable);

				int result = ss.modifyBoard(board);

				model.addAttribute("result", result);
				model.addAttribute("html", "boardmodify");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "파일 업로드 또는 저장 중 오류가 발생했습니다.");
			return "/sale/error"; // 에러 페이지로 이동
		}

		return "/sale/result";
	}

	// 게시판 삭제
	@GetMapping("/sale/BoardDelete")
	public String BoardDelete(Model model, HttpServletRequest request) {

		int seq = Integer.parseInt(request.getParameter("seq"));
		int result = ss.boardDelete(seq);

		model.addAttribute("result", result);
		model.addAttribute("html", "boarddelete");

		return "/sale/result";
	}

	// 알바 공고 등록 폼 이동
	@GetMapping("/sale/JobWriteForm")
	public String JobWriteForm(Model model, HttpServletRequest request) {

		return "/sale/JobWriteForm";
	}

// 알바 공고 등록 여부
	@PostMapping("/sale/Jobwrite")
	public String jobWrite(@ModelAttribute JobboardDTO jobBoardPostDTO, HttpServletRequest request, Model model) {

		// 아이디 불러오기
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("Sid");
		String time1 = request.getParameter("Workinghours_start");
		String time2 = request.getParameter("Workinghours_end");
		String total = time1 + " ~ " + time2;
		// 체크박스 값 처리
		String isAvailableStr = request.getParameter("is_available");
		int isAvailable = (isAvailableStr != null) ? 1 : 0;

		jobBoardPostDTO.setJBid(id);
		jobBoardPostDTO.setJBWorkinghours(total);
		jobBoardPostDTO.setJBlogtime(new Date());
		jobBoardPostDTO.setIs_available(isAvailable);

		// 폼에서 전달된 SaleboardDTO와 JobBoardPostDTO를 서비스 계층에서 처리
		int result = ss.savejobPost(jobBoardPostDTO);

		// 결과 페이지로 이동
		model.addAttribute("result", result);
		model.addAttribute("html", "JobWrite");
		return "/sale/result";
	}

	// 알바 공고 삭제
	@GetMapping("/sale/JobDelete")
	public String jobDelete(Model model, HttpServletRequest request) {

		int seq = Integer.parseInt(request.getParameter("seq"));
		int result = ss.jobDelete(seq);

		model.addAttribute("result", result);
		model.addAttribute("html", "jobDelete");

		return "/sale/result";
	}

	// 알바 수정 폼
	@PostMapping("/sale/JobInfoModifyForm")
	public String JobInfoModifyForm(Model model, HttpServletRequest request) {

		int seq = Integer.parseInt(request.getParameter("seq"));
		Jobboard board = ss.jobinfo(seq);

		model.addAttribute("seq", seq);
		model.addAttribute("board", board);

		return "/sale/JobInfoModifyForm";
	}

	// 알바 공고 수정 여부
	@PostMapping("/sale/JobModify")
	public String jobModify(@ModelAttribute JobboardDTO jobBoardPostDTO, HttpServletRequest request, Model model) {
		int seq = Integer.parseInt(request.getParameter("seq"));

		// 아이디 불러오기
		String time1 = request.getParameter("Workinghours_start");
		String time2 = request.getParameter("Workinghours_end");
		String total = time1 + " ~ " + time2;
		// 체크박스 값 처리
		String isAvailableStr = request.getParameter("is_available");
		int isAvailable = (isAvailableStr != null) ? 1 : 0;

		Jobboard board = ss.jobinfo(seq);
		System.out.println("board : " + board);

		board.setJBWorkinghours(total);
		board.setIs_available(isAvailable);
		board.setJBsub(jobBoardPostDTO.getJBsub());
		board.setJBcon(jobBoardPostDTO.getJBcon());
		board.setJBEnddate(jobBoardPostDTO.getJBEnddate());
		board.setJBPeoplenum(jobBoardPostDTO.getJBPeoplenum());
		board.setJBSalary(jobBoardPostDTO.getJBSalary());
		board.setJBSalarytype(jobBoardPostDTO.getJBSalarytype());

		int result = ss.modifyjobPost(board);

		// 결과 페이지로 이동
		model.addAttribute("result", result);
		model.addAttribute("html", "jobModify");
		return "/sale/result";
	}

	// 알바 공고 리스트 보기
	@GetMapping("/sale/Jobboardlist")
	public String jobboardlist(HttpServletRequest request, Model model) {

		HttpSession session = request.getSession();

		String id = (String) session.getAttribute("Sid");
		List<Jobboard> jblist = ss.jobAllList(id);

		// 저장된 이미지 파일 경로
		model.addAttribute("jblist", jblist);

		return "sale/joblist";
	}

	// 알바 공고 1개 보기
	@GetMapping("/sale/JobInfo")
	public String JobInfo(HttpServletRequest request, Model model) {

		int seq = Integer.parseInt(request.getParameter("seq"));

		Jobboard board = ss.jobinfo(seq);
		model.addAttribute("board", board);
		return "/sale/JobInfo";

	}

}
