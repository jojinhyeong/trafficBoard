package com.jjh.board.service;

import com.jjh.board.member.dto.MemberDTO;
import com.jjh.board.member.entity.Member;
import com.jjh.board.member.repository.MemberRepository;
import com.jjh.board.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class LoginTest {

    @Autowired
    private MemberService memberService;

    @MockitoBean
    private MemberRepository memberRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Test
    void login_success() {
        // Given: Mock 데이터 준비
        String loginId = "user1";
        String rawPassword = "1111";

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Member mockMember = Member.builder()
                .id(21L)
                .loginId(loginId)
                .password(encodedPassword)
                .name("회원1")
                .email("user1@example.com")
                .build();

        Mockito.when(memberRepository.findByLoginId(loginId)).thenReturn(Optional.of(mockMember));
        Mockito.when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        // When: 로그인 메서드 호출
        MemberDTO result = memberService.login(loginId, rawPassword);

        // Then: 결과 검증
        assertThat(result).isNotNull();
        assertThat(result.getLoginId()).isEqualTo(loginId);
        assertThat(result.getName()).isEqualTo("회원1");
    }

    @Test
    void login_fail_wrong_password() {
        // Given: Mock 데이터 준비
        String loginId = "user1";
        String rawPassword = "wrong_password";

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode("1111");

        Member mockMember = Member.builder()
                .id(1L)
                .loginId(loginId)
                .password(encodedPassword)
                .name("회원1")
                .email("user1@example.com")
                .build();

        Mockito.when(memberRepository.findByLoginId(loginId)).thenReturn(Optional.of(mockMember));
        Mockito.when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        // When: 로그인 메서드 호출
        MemberDTO result = memberService.login(loginId, rawPassword);

        // Then: 결과 검증
        assertThat(result).isNull(); // 비밀번호가 틀렸으므로 null 반환
    }

    @Test
    void login_fail_user_not_found() {
        // Given: Mock 데이터 준비
        String loginId = "nonexistent_user";
        String rawPassword = "1111";

        Mockito.when(memberRepository.findByLoginId(loginId)).thenReturn(Optional.empty());

        // When: 로그인 메서드 호출
        MemberDTO result = memberService.login(loginId, rawPassword);

        // Then: 결과 검증
        assertThat(result).isNull(); // 사용자가 없으므로 null 반환
    }
}
