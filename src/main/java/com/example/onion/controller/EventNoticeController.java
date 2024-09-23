package com.example.onion.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.onion.dto.UserInfoDTO;
import com.example.onion.entity.Manager_board;
import com.example.onion.service.Manager_boardService;
import com.example.onion.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/main")
public class EventNoticeController {

    @Autowired
    Manager_boardService service;
    
    @Autowired
    UserService userService;

    @Value("${project.upload.path}")
    private String uploadPath;

    @GetMapping("/event-notice")
    public String eventNotice(Model model, HttpServletRequest request) {
        int pg = 1;
        if (request.getParameter("pg") != null && !request.getParameter("pg").equals("")) {
            pg = Integer.parseInt(request.getParameter("pg"));
        }
        
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

        // 한 페이지당 8개의 아이템을 표시하도록 수정
        int itemsPerPage = 9;
        int endNum = pg * itemsPerPage;
        int startNum = endNum - (itemsPerPage - 1);

        List<Manager_board> list = service.noticeventList(startNum, endNum);

        int totalA = service.getTotalA();
        // 전체 페이지 수 계산을 8개의 아이템으로 변경
        int totalP = (totalA + itemsPerPage - 1) / itemsPerPage;

        int startPage = (pg - 1) / 3 * 3 + 1;
        int endPage = startPage + 2;
        if (endPage > totalP)
            endPage = totalP;

        model.addAttribute("list", list);
        model.addAttribute("pg", pg);
        model.addAttribute("totalP", totalP);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("uploadPath", uploadPath);

        System.out.println("list");
        return "/main/event-notice";  
    }
    
    
    @GetMapping("/event-noticeView")
    public String eventnoticeView (Model model, HttpServletRequest request,
            @RequestParam("seq") int seq,
            @RequestParam("pg") int pg) {
        
        Manager_board manager_board = service.noticeventView(seq);
        
        model.addAttribute("dto", manager_board);
        model.addAttribute("pg", pg);
        
        return "/main/event-noticeView";
    }


}
