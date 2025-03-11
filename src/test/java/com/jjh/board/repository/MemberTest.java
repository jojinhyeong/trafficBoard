package com.jjh.board.repository;

import com.jjh.board.member.entity.Member;
import com.jjh.board.member.entity.MemberRole;
import com.jjh.board.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 10명 추가 테스트")
    void insertMembers() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            String loginId = "user" + i;

            // 중복 검사
            if (memberRepository.findByLoginId(loginId).isPresent()) {
                System.out.println("중복된 loginId: " + loginId);
                return; // 중복된 경우 저장하지 않음
            }

            Member member = Member.builder()
                    .loginId(loginId)
                    .password(passwordEncoder.encode("1111"))
                    .name("회원" + i)
                    .birthDate(LocalDateTime.of(1990, 1, i, 0, 0))
                    .email("user" + i + "@example.com")
                    .build();

            // 권한 설정 (기본 USER, 5번마다 MANAGER 추가, 10번 회원은 ADMIN까지 추가)
            member.addRole(MemberRole.USER);

            if (i % 5 == 0) {
                member.addRole(MemberRole.MANAGER);
            }
            if (i == 10) {
                member.addRole(MemberRole.ADMIN);
            }

            memberRepository.save(member);
        });
    }

    @Test
    @DisplayName("loginId로 Member와 Roles 조회 테스트")
    void findByLoginIdWithRolesTest() {
        String loginId = "user10";

        Member member = memberRepository.findByLoginIdWithRoles(loginId);

        assertNotNull(member, "회원이 존재하지 않습니다!");
        System.out.println("조회된 회원: " + member.getLoginId());
        System.out.println("회원 이름: " + member.getName());
        System.out.println("회원 이메일: " + member.getEmail());
        System.out.print("회원 역할: ");
        member.getMemberRoleList().forEach(role -> System.out.print(role + " "));
        System.out.println();
    }

}
