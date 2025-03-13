package com.jjh.board.member.service;

import com.jjh.board.member.dto.MemberDTO;
import com.jjh.board.member.entity.Member;
import com.jjh.board.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }
    /**
     * 로그인 처리
     *
     * @param loginId 로그인 아이디
     * @param password 비밀번호
     * @return 로그인 성공 시 MemberDTO 반환, 실패 시 null 반환
     */
    /*public MemberDTO login(String loginId, String password) {
        // 데이터베이스에서 사용자 조회
        Member member = memberRepository.findByLoginId(loginId)
                .orElse(null);

        if (member == null) {
            log.warn("사용자를 찾을 수 없습니다: {}", loginId);
            return null; // 사용자 없음
        }

        // 비밀번호 검증
        if (passwordEncoder.matches(password, member.getPassword())) {
            log.info("로그인 성공: {}", loginId);
            // 엔티티를 DTO로 변환하여 반환
            return member.toDTO();
        } else {
            log.warn("비밀번호가 일치하지 않습니다: {}", loginId);
            return null; // 비밀번호 불일치
        }
    }*/

    /**
     * 로그인 아이디로 사용자 정보와 권한 정보를 가져오는 메서드
     */
    public MemberDTO getMemberWithRoles(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + loginId));

        // 엔티티를 DTO로 변환하여 반환
        return member.toDTO();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        log.info("로그인 loadUserByUsername");
        // Lazy 로딩된 컬렉션 초기화
        Hibernate.initialize(member.getMemberRoleList());
        log.info("로그인 member1");
        log.info(String.valueOf(member.getLoginId()));
        log.info(String.valueOf(member.getPassword()));
        log.info(String.valueOf(member.getMemberRoleList()));
        log.info("로그인 member2");
        return User.builder()
                .username(member.getLoginId())
                .password(member.getPassword())
                .authorities(member.getMemberRoleList().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()))
                .build();
    }
}
