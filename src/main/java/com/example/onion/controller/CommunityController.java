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

import com.example.onion.dto.CommentDTO;
import com.example.onion.dto.CommunityDTO;
import com.example.onion.dto.UserInfoDTO;
import com.example.onion.entity.Comment;
import com.example.onion.entity.Community;
import com.example.onion.service.CommentService;
import com.example.onion.service.CommunityService;
import com.example.onion.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CommunityController {

   @Value("${project.upload.path}")
   private String uploadpath;

   @Autowired
   CommunityService service;

   @Autowired
   UserService userService;

   @GetMapping("/community/communityList")
   public String communityList(Model model, HttpServletRequest request) {

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

          int pg = 1;
          if (request.getParameter("pg") != null && !request.getParameter("pg").equals("")) {
              pg = Integer.parseInt(request.getParameter("pg"));
          }
          int endNum = pg * 5;
          int startNum = endNum - 4;

          String search = request.getParameter("search");
          String sort = request.getParameter("sort"); // 정렬 조건을 받음

          List<Community> list;
          if ("like".equals(sort)) {
              list = service.communityListByLike(startNum, endNum, search);
          } else if ("hit".equals(sort)) {
              list = service.communityListByHit(startNum, endNum, search);
          } else {
              list = service.communityList(startNum, endNum, search); // 최신순
          }

          int totalA = service.getTotalA();
          int totalP = (totalA + 4) / 5;

          int startPage = (pg - 1) / 3 * 3 + 1;
          int endPage = startPage + 2;
          if (endPage > totalP)
              endPage = totalP;

          model.addAttribute("list", list);
          model.addAttribute("pg", pg);
          model.addAttribute("totalP", totalP);
          model.addAttribute("startPage", startPage);
          model.addAttribute("endPage", endPage);
          model.addAttribute("uploadpath", uploadpath);
          model.addAttribute("search", search); // 검색어를 모델에 추가
          model.addAttribute("sort", sort); // 정렬 조건을 모델에 추가
          return "community/communityList";
   }

   @GetMapping("/community/communityWriteForm")
   public String communityWriteForm(Model model) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String uid = authentication.getName();

      UserInfoDTO userInfo = userService.getMemberById1(uid);

      String id = userInfo.getUserid();
      String name = userInfo.getUname();

      model.addAttribute("userid", id);
      model.addAttribute("username", name);

      return "community/communityWriteForm";
   }

   @PostMapping("/community/communityWrite")
   public String communityWrite(CommunityDTO dto, Model model, @RequestParam("img") MultipartFile uploadFile) {

//      try { 콘솔창을 볼수없는 경우면 파일 저장해보는 코드
//         FileWriter fileWriter = new FileWriter(new File("D:/test.txt"));
//         fileWriter.write("test");
//         fileWriter.flush();
//         fileWriter.close();
//      } catch (IOException e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      }
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String uid = authentication.getName();

      UserInfoDTO userInfo = userService.getMemberById1(uid);

      String id = userInfo.getUserid();
      String name = userInfo.getUname();

      // 로그인한 사용자의 ID를 작성자로 설정
      dto.setCid(id);
      dto.setCname(name);
      model.addAttribute("cid", id);
      model.addAttribute("cname", name);

      String fileName = uploadFile.getOriginalFilename();
      dto.setCimg(fileName);
      dto.setClogtime(new Date());

      if (!fileName.equals("")) {
         File file = new File(uploadpath, fileName);

         try {
            uploadFile.transferTo(file);
         } catch (IllegalStateException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         }
         boolean result = service.communityWrite(dto);
         model.addAttribute("result", result);

      }
      return "community/communityWrite";
   }

   @GetMapping("/community/communityView")
   public String communityView(Model model, HttpServletRequest request) {
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

      String seqParam = request.getParameter("seq");
      String pgParam = request.getParameter("pg");

      int seq = (seqParam != null && !seqParam.isEmpty()) ? Integer.parseInt(seqParam) : 0; // 기본값 설정
      int pg = (pgParam != null && !pgParam.isEmpty()) ? Integer.parseInt(pgParam) : 1; // 기본값 설정

      // 커뮤니티 정보와 조회수 증가
      Community community = service.communityView(seq);
      model.addAttribute("dto", community);
      model.addAttribute("pg", pg);

      // 댓글 목록을 추가
      List<Comment> comments = commentService.getCommentsByCommunityId(seq);
      model.addAttribute("comments", comments);

      return "community/communityView";
   }

   // 좋아요 버튼을 클릭했을 때 요청을 처리
   @GetMapping("/community/communityLike")
   public String likeCommunity(@RequestParam("seq") int cseq, @RequestParam("pg") int pg,
         RedirectAttributes redirectAttributes, Model model) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String uid = authentication.getName();

      UserInfoDTO userInfo = userService.getMemberById1(uid);

      String id = userInfo.getUserid();
      String name = userInfo.getUname();

      model.addAttribute("userid", id);
      model.addAttribute("username", name);

      // 좋아요 수 증가
      service.likeCommunity(cseq);

      // 리다이렉트 시 현재 페이지 정보 유지
      redirectAttributes.addAttribute("pg", pg);

      // 상세 페이지로 리다이렉트
      return "redirect:/community/communityView?seq=" + cseq;
   }

   @GetMapping("/community/communityDelete")
   public String communityDelete(Model model, HttpServletRequest request) {
      int seq = Integer.parseInt(request.getParameter("seq"));
      int pg = Integer.parseInt(request.getParameter("pg"));

      // 댓글 삭제
      commentService.deleteCommentsByCommunityId(seq);

      // 게시글 삭제
      boolean result = service.communityDelete(seq);

      model.addAttribute("result", result);
      model.addAttribute("pg", pg);

      return "community/communityDelete";
   }

   @GetMapping("/community/communityModifyForm")
   public String communityModifyForm(@RequestParam("seq") int seq, Model model) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String uid = authentication.getName();

      UserInfoDTO userInfo = userService.getMemberById1(uid);

      String id = userInfo.getUserid();
      String name = userInfo.getUname();

      model.addAttribute("userid", id);
      model.addAttribute("username", name);

      // 수정할 게시글 정보를 가져옵니다
      Community community = service.communityView(seq);
      if (community == null) {
         // 게시글이 없는 경우 에러 처리
         return "redirect:/community/communityList";
      }
      model.addAttribute("community", community);
      return "community/communityModifyForm";
   }

   @PostMapping("/community/communityModify")
   public String communityModify(CommunityDTO dto, @RequestParam("img") MultipartFile uploadFile, Model model) {
      // 파일 업로드 처리
      String fileName = uploadFile.getOriginalFilename();
      if (!fileName.isEmpty()) {
         File file = new File(uploadpath, fileName);

         try {
            uploadFile.transferTo(file);
            dto.setCimg(fileName); // 업로드된 이미지 이름을 DTO에 설정
         } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
         }
      }

      // 수정된 게시글을 서비스에 전달
      boolean result = service.updateCommunity(dto);
      model.addAttribute("result", result);

      return "redirect:/community/communityView?seq=" + dto.getCseq();
   }

   @Autowired
   CommentService commentService;

   @PostMapping("/community/addComment")
   public String addComment(CommentDTO commentDTO, RedirectAttributes redirectAttributes) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String uid = authentication.getName();

      UserInfoDTO userInfo = userService.getMemberById1(uid);
      commentDTO.setUserId(userInfo.getUserid()); // 로그인된 사용자의 ID를 댓글 작성자로 설정

      commentService.addComment(commentDTO); // 댓글 추가

      // 커뮤니티 글 상세 페이지로 리다이렉트, 현재 보고 있는 게시물로 이동
      redirectAttributes.addAttribute("seq", commentDTO.getCommunityId());
      return "redirect:/community/communityView";
   }

   @GetMapping("/community/editCommentForm")
   public String editCommentForm(@RequestParam("commentId") int commentId,
         @RequestParam("communityId") int communityId, Model model) {
      Comment comment = commentService.getCommentById(commentId);
      if (comment == null) {
         return "redirect:/community/communityView?seq=" + communityId;
      }
      model.addAttribute("comment", comment);
      model.addAttribute("communityId", communityId);
      return "community/editCommentForm";
   }

   @PostMapping("/community/updateComment")
   public String updateComment(CommentDTO commentDTO, RedirectAttributes redirectAttributes) {
      commentService.updateComment(commentDTO);
      redirectAttributes.addAttribute("seq", commentDTO.getCommunityId());
      return "redirect:/community/communityView";
   }

   @GetMapping("/community/deleteComment")
   public String deleteComment(@RequestParam("commentId") int commentId, @RequestParam("communityId") int communityId,
         RedirectAttributes redirectAttributes) {
      // 댓글 삭제 로직
      commentService.deleteComment(commentId);

      // 리다이렉트
      redirectAttributes.addAttribute("seq", communityId);
      return "redirect:/community/communityView";
   }
}
