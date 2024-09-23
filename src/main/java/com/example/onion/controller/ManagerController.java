package com.example.onion.controller;

import com.example.onion.dto.BannerDTO;
import com.example.onion.dto.ManagerDTO;
import com.example.onion.dto.Manager_boardDTO;
import com.example.onion.entity.Boardinfo;
import com.example.onion.entity.Community;
import com.example.onion.entity.Manager_board;
import com.example.onion.entity.Userinfo;

import com.example.onion.repository.UserRepository;
import com.example.onion.service.BannerService;
import com.example.onion.service.CommentService;
import com.example.onion.service.CommunityService;
import com.example.onion.service.ManagerService;
import com.example.onion.service.Manager_boardService;
import com.example.onion.service.ProfileService;
import com.example.onion.service.ReportService;
import com.example.onion.service.WordService;
import com.example.onion.service.boardInfoService;

import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager")
public class ManagerController {

   private final ManagerService managerService;
   private final ProfileService profileService;
   private final WordService wordService;
   private final ReportService reportService;
   private final UserRepository userRepository;
   private final BannerService bannerService; // 배너 서비스 추가
   private final Manager_boardService service;

   @Value("${project.upload.path}")
   private String uploadPath;

   @Autowired
   CommentService commentservice;

   @Autowired
   CommunityService communityService;

   @Autowired
   boardInfoService bs;

   // 생성자 주입 방식으로 의존성 주입
   public ManagerController(ManagerService managerService, ProfileService profileService, WordService wordService,
         ReportService reportService, UserRepository userRepository, BannerService bannerService,
         Manager_boardService service) { // bannerService 추가
      this.managerService = managerService;
      this.profileService = profileService;
      this.wordService = wordService;
      this.reportService = reportService;
      this.userRepository = userRepository;
      this.bannerService = bannerService; // 배너 서비스 주입
      this.service = service;
   }

   // 매니저 대시보드 페이지
   @GetMapping("/dashboard")
   public String managerDashboard(Model model) {
      model.addAttribute("message", "관리자 전용 대시보드");
      return "manager/dashboard"; // templates/manager/dashboard.html
   }

   // 매니저 등록 폼
   @GetMapping("/registerForm")
   public String registerForm(Model model) {
      model.addAttribute("req", "/manager/register"); // 관리자 등록 폼 HTML 페이지 반환
      return "manager/dashboard";
   }
   
   // 매니저 등록 처리
   // 매니저 등록 처리
   @PostMapping("/register")
   public String registerManager(@ModelAttribute ManagerDTO managerDTO, Model model, RedirectAttributes redirectAttributes) {
       try {
           // 현재 로그인한 사용자 가져오기
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           String currentUserId = authentication.getName();

           // admin이 아니면 등록 불가
           if (!"admin".equals(currentUserId)) {
               redirectAttributes.addFlashAttribute("errorMessage", "매니저 등록은 최고관리자만 가능합니다.");
               return "redirect:/manager/registerForm"; // 에러 메시지와 함께 리다이렉트
           }


           // 입력 값 검증
           if (managerDTO.getMid() == null || managerDTO.getMid().isEmpty() || managerDTO.getMpwd() == null
                   || managerDTO.getMpwd().isEmpty()) {
               model.addAttribute("errorMessage", "아이디와 비밀번호는 필수 입력 항목입니다.");
               return "redirect:/manager/register"; // 에러 메시지를 전달하고 다시 등록 폼으로 돌아감
           }

           // 중복된 아이디 처리
           if (managerService.getManagerById(managerDTO.getMid()) != null) {
               model.addAttribute("errorMessage", "이미 존재하는 아이디입니다.");
               return "redirect:/manager/register"; // 에러 메시지를 전달하고 다시 등록 폼으로 돌아감
           }

           // 매니저 등록 처리
           managerService.registerManager(managerDTO.getMid(), managerDTO.getMpwd(), managerDTO.getRole());
           return "redirect:/manager/list";
       } catch (Exception e) {
           e.printStackTrace();
           model.addAttribute("errorMessage", "매니저 등록 중 오류가 발생했습니다.");
           return "redirect:/manager/register"; // 예외 발생 시 등록 페이지로 돌아감
       }
   }



   // 매니저 목록 조회
   @GetMapping("/list")
   public String listManagers(Model model) {
      model.addAttribute("managers", managerService.findAllManagers());
      model.addAttribute("req", "/manager/list");
      return "manager/dashboard";
   }

   // 매니저 정보 조회
   @GetMapping("/{mid}")
   public String getManager(@PathVariable String mid, Model model) {
      ManagerDTO manager = managerService.getManagerById(mid);
      model.addAttribute("manager", manager);
      model.addAttribute("req", "/manager/detail"); // 관리자 상세 정보 페이지
      return "manager/dashboard";
   }

   // 매니저 업데이트 폼
   @GetMapping("/update/{mid}")
   public String updateForm(@PathVariable("mid") String mid, Model model, RedirectAttributes redirectAttributes) {
       // 현재 로그인한 사용자 가져오기
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String currentUserId = authentication.getName();

       // admin이 아니면 수정 폼 접근 불가
       if (!"admin".equals(currentUserId)) {
           redirectAttributes.addFlashAttribute("errorMessage", "매니저 수정 최고관리자만 가능합니다.");
           return "redirect:/manager/list"; // 에러 메시지를 전달하고 목록 페이지로 리다이렉트
       }

       ManagerDTO manager = managerService.getManagerById(mid);
       model.addAttribute("manager", manager);
       model.addAttribute("req", "/manager/update"); // 관리자 상세 정보 페이지

       return "manager/update"; // 관리자 수정 폼으로 이동
   }

   // 매니저 업데이트 처리
   @PostMapping("/update")
   public String updateManager(@ModelAttribute ManagerDTO managerDTO, Model model, RedirectAttributes redirectAttributes) {
       // 현재 로그인한 사용자 가져오기
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String currentUserId = authentication.getName();

       // admin이 아니면 업데이트 불가
       if (!"admin".equals(currentUserId)) {
           redirectAttributes.addFlashAttribute("errorMessage", "매니저 수정 최고관리자만 가능합니다.");
           return "redirect:/manager/list"; // 에러 메시지를 전달하고 목록 페이지로 리다이렉트
       }

       managerService.updateManager(managerDTO);
       return "redirect:/manager/list"; // 수정 후 목록 페이지로 리다이렉트
   }



   // 매니저 삭제 처리
   @PostMapping("/delete/{mid}")
   public String deleteManager(@PathVariable("mid") String mid, Model model, RedirectAttributes redirectAttributes) {
       // 현재 로그인한 사용자 가져오기
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String currentUserId = authentication.getName();

       // admin이 아니면 삭제 불가
       if (!"admin".equals(currentUserId)) {
           redirectAttributes.addFlashAttribute("errorMessage", "매니저 삭제는 최고관리자만 가능합니다.");
           return "redirect:/manager/list"; // 에러 메시지를 전달하고 목록 페이지로 리다이렉트
       }

       managerService.deleteManager(mid);
       return "redirect:/manager/list";
   }



   // 프로필 목록 조회
   @GetMapping("/profileList")
   public String profileList(Model model, @RequestParam(name = "pg", defaultValue = "1") int pg) {
      int pageSize = 10;
      int startNum = (pg - 1) * pageSize + 1;
      int endNum = pg * pageSize;

      Map<String, Integer> map = new HashMap<String, Integer>();

      List<Userinfo> list = profileService.getUserList(startNum, endNum);
      Integer commentcount = 0;
      Integer totalboardcount = 0;
      Integer boardcount = 0;
      Integer communitycount = 0;

      for (Userinfo user : list) {
         String id = user.getUserid();
         commentcount = commentservice.count(id);
         boardcount = bs.boardcount(id);
         communitycount = communityService.communitycount(id);
         totalboardcount = boardcount + communitycount;
      }

      int totalA = (int) profileService.count();
      int totalP = (int) Math.ceil((double) totalA / pageSize);

      int startPage = (pg - 1) / 5 * 5 + 1;
      int endPage = Math.min(startPage + 4, totalP);

      model.addAttribute("list", list);
      model.addAttribute("pg", pg);
      model.addAttribute("totalA", totalA);
      model.addAttribute("startPage", startPage);
      model.addAttribute("endPage", endPage);
      model.addAttribute("totalP", totalP);
      model.addAttribute("commentcount", commentcount);
      model.addAttribute("totalboardcount", totalboardcount);
      model.addAttribute("req", "/manager/profileList");

      return "manager/dashboard";
   }

   @GetMapping("/profileView_m")
   public String profileView_m(@RequestParam(value = "userid", required = false) String userid,
         @RequestParam(value = "pg", defaultValue = "1") int pg, Model model) {
      // 로그로 userid와 pg 확인
      System.out.println("userid: " + userid);
      System.out.println("pg: " + pg);

      if (userid == null || userid.isEmpty()) {
         return "redirect:/manager/profileList"; // userid가 없을 때 에러 페이지로 리다이렉트
      }

      Userinfo user = profileService.getUserById(userid);
      List<Community> CommunityList = communityService.communityList(userid);
      List<Boardinfo> BoardList = bs.boardList(userid);

      model.addAttribute("user", user);
      model.addAttribute("CommunityList", CommunityList);
      model.addAttribute("BoardList", BoardList);
      model.addAttribute("userid", userid);
      model.addAttribute("pg", pg);
      model.addAttribute("req", "/manager/profileView_m");
      return "manager/dashboard";
   }

   @GetMapping("/noticeventList")
   public String noticeventList(Model model, HttpServletRequest request) {

      // 현재 인증된 사용자의 인증 정보 가져오기
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      String id = authentication.getName();
      System.out.println("id : " + id);

      int pg = 1;

      if (request.getParameter("pg") != null && !request.getParameter("pg").equals("")) {
         pg = Integer.parseInt(request.getParameter("pg"));
      }

      int itemsPerPage = 7; // 한 페이지당 항목 수를 7로 설정
      int endNum = pg * itemsPerPage;
      int startNum = endNum - (itemsPerPage - 1);
      List<Manager_board> list = service.noticeventList(startNum, endNum);
      int totalA = service.getTotalA();
      int totalP = (totalA + (itemsPerPage - 1)) / itemsPerPage;

      int startPage = (pg - 1) / 3 * 3 + 1;
      int endPage = startPage + 2;
      if (endPage > totalP)
         endPage = totalP;

      // 절대 경로 설정
      String absoluteUploadPath = uploadPath; // 절대 경로로 설정된 업로드 경로 사용

      // 모델에 속성 추가
      model.addAttribute("list", list);
      model.addAttribute("pg", pg);
      model.addAttribute("totalP", totalP);
      model.addAttribute("startPage", startPage);
      model.addAttribute("endPage", endPage);
      model.addAttribute("uploadPath", absoluteUploadPath); // 절대 경로 사용
      model.addAttribute("req", "/manager/noticeventList");

      // view 파일 선택
      return "/manager/dashboard";
   }

   // 게시글 폼
   @GetMapping("/noticeventWrite")
   public String noticeventWrite(Model model) {

      model.addAttribute("req", "/manager/noticeventWrite");
      return "/manager/dashboard";
   }

   // 게시글 저장하기
   @PostMapping("/noticevent")
   public String noticevent(Manager_boardDTO dto, Model model, @RequestParam("img") MultipartFile uploadFile,
         HttpServletRequest request) {

      // 현재 인증된 사용자의 인증 정보 가져오기
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String id = authentication.getName();

      String fileName = uploadFile.getOriginalFilename();
      dto.setMBlogtime(new Date());
      dto.setMBimg(fileName);
      dto.setMBid(id);

      if (!fileName.equals("")) {
         File file = new File(uploadPath, fileName);

         try {
            uploadFile.transferTo(file);
         } catch (IllegalStateException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         }

         boolean result = service.noticeventWrite(dto);
         model.addAttribute("result", result);
         model.addAttribute("req", "/manager/noticevent");
      }
      return "/manager/dashboard"; // 공지사항 및 이벤트 작성 페이지
   }

   @GetMapping("/noticeventView")
   public String noticeventView(Model model, HttpServletRequest request) {
      String seqParam = request.getParameter("seq");
      String pgParam = request.getParameter("pg");

      int seq = (seqParam != null && !seqParam.isEmpty()) ? Integer.parseInt(seqParam) : 1; // 기본값 설정
      int pg = (pgParam != null && !pgParam.isEmpty()) ? Integer.parseInt(pgParam) : 1; // 기본값 설정

      Manager_board manager_board = service.noticeventView(seq);

      model.addAttribute("dto", manager_board);
      model.addAttribute("pg", pg);
      model.addAttribute("req", "/manager/noticeventView");
      return "/manager/dashboard";
   }

   @GetMapping("/noticeventModifyForm")
   public String noticeventModifyForm(@RequestParam("seq") int seq, Model model) {
      // 공지사항/이벤트 데이터를 가져옴
      Manager_board manager_board = service.noticeventView(seq);

      // 모델에 데이터 추가
      model.addAttribute("manager_board", manager_board);
      model.addAttribute("req", "/manager/noticeventModifyForm");
      // 수정 폼 페이지 반환
      return "/manager/dashboard";
   }

   @PostMapping("/noticeventModify")
   public String noticeventModify(@ModelAttribute Manager_boardDTO dto, @RequestParam("img") MultipartFile uploadFile,
         Model model) {

      // 파일이 있는 경우 업로드 처리
      if (!uploadFile.isEmpty()) {
         String fileName = uploadFile.getOriginalFilename();
         // String filePath = uploadPath + File.separator + fileName; // 절대 경로로 파일 저장 경로
         // 생성
         File file = new File(uploadPath, fileName);

         try {
            // 파일 저장
            uploadFile.transferTo(file);
            dto.setMBimg(fileName); // 상대 경로로 DTO에 파일명 설정
         } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "이미지 업로드 실패");
            return "manager/noticeventModifyForm"; // 에러 시 수정 폼으로 돌아감
         }
      } else {
         // 이미지가 변경되지 않았을 때, 기존 이미지 유지
         Manager_board existingData = service.noticeventView(dto.getMBseq());
         if (existingData != null) {
            dto.setMBimg(existingData.getMBimg());
         }
      }

      // 수정된 데이터 업데이트
      boolean result = service.updateNoticevent(dto);
      model.addAttribute("result", result);
      model.addAttribute("req", "/manager/noticeventModify");

      // 수정 완료 후 리스트 페이지로 리다이렉트
      return result ? "redirect:/manager/noticeventList" : "manager/noticeventModifyForm";
   }

   @GetMapping("/noticeventDelete")
   public String noticeventDelete(@RequestParam("seq") int seq, @RequestParam("pg") int pg, Model model) {
      // 공지/이벤트 삭제 시도

      boolean result = service.noticeventDelete(seq);
      System.out.println("result : " + result);

      // 결과와 현재 페이지를 모델에 추가
      model.addAttribute("result", result);
      model.addAttribute("pg", pg);
      model.addAttribute("req", "/manager/noticeventDelete");
      // JavaScript 처리를 위한 올바른 템플릿 반환
      return "/manager/dashboard";
   }

   // 배너 관리 페이지
   @GetMapping("/banner")
   public String showBannerManagement(Model model, HttpServletRequest request ) {
      // 현재 인증된 사용자의 인증 정보 가져오기
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      String id = authentication.getName();
      System.out.println("id : " + id);

      int pg = 1;

      if (request.getParameter("pg") != null && !request.getParameter("pg").equals("")) {
         pg = Integer.parseInt(request.getParameter("pg"));
      }

      int itemsPerPage = 7; // 한 페이지당 항목 수를 7로 설정
      int endNum = pg * itemsPerPage;
      int startNum = endNum - (itemsPerPage - 1);
      List<Manager_board> list = service.noticeventList(startNum, endNum);
      int totalA = service.getTotalA();
      int totalP = (totalA + (itemsPerPage - 1)) / itemsPerPage;

      int startPage = (pg - 1) / 3 * 3 + 1;
      int endPage = startPage + 2;
      if (endPage > totalP)
         endPage = totalP;

      // 절대 경로 설정
      String absoluteUploadPath = uploadPath; // 절대 경로로 설정된 업로드 경로 사용

      // 모델에 속성 추가
      model.addAttribute("list", list);
      model.addAttribute("pg", pg);
      model.addAttribute("totalP", totalP);
      model.addAttribute("startPage", startPage);
      model.addAttribute("endPage", endPage);
      model.addAttribute("uploadPath", absoluteUploadPath); // 절대 경로 사용
      List<BannerDTO> bannerList = bannerService.getAllBanners();
      System.out.println("배너 목록: " + bannerList);
      model.addAttribute("banners", bannerList); // 'banners' 이름으로 모델에 추가
      model.addAttribute("req", "/manager/banner-management"); // 배너 관리 페이지
      return "/manager/dashboard";
   }

   // 배너 추가 페이지
   @GetMapping("/banner-make")
   public String showBannerMakeForm(Model model) {

      model.addAttribute("req", "/manager/banner-make");
      return "/manager/dashboard";
   }

   @PostMapping("/banner/add")
   public String addBanner(@RequestParam("imageFile") MultipartFile imageFile, @RequestParam("title") String title,
         @RequestParam("description") String description, @RequestParam("isActive") Boolean isActive) {
      if (!imageFile.isEmpty()) {
         try {
            // 파일 저장 경로 설정
            String filePath = uploadPath + "/" + imageFile.getOriginalFilename();

            // 파일을 서버에 저장
            imageFile.transferTo(new File(filePath));

            // 저장된 파일 경로를 웹 URL로 설정
            String imageUrl = "/img/banner/" + imageFile.getOriginalFilename();

            // 배너 정보 저장
            BannerDTO bannerDTO = new BannerDTO(null, imageUrl, title, description, isActive, null, null);
            bannerService.addBanner(bannerDTO);
         } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/manager/banner?error";
         }
      }
      return "redirect:/manager/dashboard";
   }

   @GetMapping("/banner-edit/{id}")
   public String editBannerForm(@PathVariable("id") Long bannerId, Model model) {
      // 매개변수 이름을 명시적으로 지정
      BannerDTO bannerDTO = bannerService.getBannerById(bannerId);
      model.addAttribute("banner", bannerDTO);
      model.addAttribute("req", "/manager/banner-edit");
      return "/manager/dashboard";
   }

   @PostMapping("/banner/update")
   public String updateBanner(@RequestParam("id") Long id, @RequestParam("imageFile") MultipartFile imageFile,
         @RequestParam("title") String title, @RequestParam("description") String description,
         @RequestParam("isActive") Boolean isActive) {
      try {
         // 기존 배너 가져오기
         BannerDTO bannerDTO = bannerService.getBannerById(id);

         // 새로운 이미지 파일이 업로드된 경우에만 이미지 업데이트
         if (!imageFile.isEmpty()) {
            String filePath = uploadPath + "/" + imageFile.getOriginalFilename();
            imageFile.transferTo(new File(filePath));
            String imageUrl = "/img/banner/" + imageFile.getOriginalFilename();
            bannerDTO.setImageUrl(imageUrl);
         }

         // 다른 필드 업데이트
         bannerDTO.setTitle(title);
         bannerDTO.setDescription(description);
         bannerDTO.setIsActive(isActive);

         // 배너 업데이트
         bannerService.updateBanner(id, bannerDTO);
      } catch (IOException e) {
         e.printStackTrace();
         return "redirect:/manager/banner/edit/" + id + "?error";
      }

      return "redirect:/manager/dashboard";
   }

}
