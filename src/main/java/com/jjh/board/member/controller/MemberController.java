package com.jjh.board.member.controller;

import com.jjh.board.member.dto.MemberDTO;
import com.jjh.board.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    /**
     * 로그인 페이지 이동
     */
    @GetMapping("/login")
    public String login() {
        return "login"; // login.html 템플릿 반환
    }

    /**
     * 로그인 처리
     */
  /*  @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        log.info("로그인 시작");

        // 사용자 인증 로직 수행
        MemberDTO memberDTO = memberService.authenticate(username, password);

        if (memberDTO != null) {
            log.info("로그인 성공");
            session.setAttribute("loginUser", memberDTO);
            return "redirect:/main";
        } else {
            log.warn("로그인 실패");
            return "redirect:/login?error=true"; // 로그인 실패 시 다시 로그인 페이지로 이동
        }
    }*/

    /**
     * 메인 페이지 이동
     */
    @PreAuthorize("hasRole('USER')") // ROLE_USER 권한이 있는 사용자만 접근 가능
    @GetMapping("/main")
    public String mainPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        // 사용자 정보를 DTO로 가져오기
        MemberDTO memberDTO = memberService.getMemberWithRoles(user.getUsername());

        model.addAttribute("username", memberDTO.getName());
        return "main"; // main.html 템플릿 반환
    }

    /**
     * 로그아웃 처리
     */
/*    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화 (로그아웃)
        return "redirect:/login"; // 로그아웃 후 로그인 페이지로 이동
    }*/

}