package com.jjh.board.member.controller;

import com.jjh.board.member.dto.MemberDTO;
import com.jjh.board.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 로그인 페이지 이동
     */
    @GetMapping("/login")
    public String login() {
        return "login"; // login.html 템플릿 반환
    }

    @PostMapping("/login")
    public String login(MemberDTO memberDTO, HttpSession session, Model model) {
        MemberDTO loginResult = memberService.login(memberDTO.getLoginId(), memberDTO.getPassword());

        if (loginResult != null) {
            session.setAttribute("loginUser", loginResult); // 세션에 사용자 정보 저장
            return "redirect:/main"; // 로그인 성공 시 메인 페이지로 이동
        } else {
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "login"; // 로그인 실패 시 다시 로그인 페이지로 이동
        }
    }

    /**
     * 메인 페이지 이동
     */
    @GetMapping("/main")
    public String mainPage(HttpSession session, Model model) {
        // 세션에서 사용자 정보 가져오기
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login"; // 세션에 사용자 정보가 없으면 로그인 페이지로 이동
        }

        model.addAttribute("message", "안녕하세요, " + loginUser.getName() + "님!");
        return "main"; // main.html 템플릿 반환
    }

    /**
     * 로그아웃 처리
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화 (로그아웃)
        return "redirect:/login"; // 로그아웃 후 로그인 페이지로 이동
    }

}