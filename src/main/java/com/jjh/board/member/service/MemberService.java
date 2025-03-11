package com.jjh.board.member.service;

import com.jjh.board.member.dto.MemberDTO;
import com.jjh.board.member.entity.Member;
import com.jjh.board.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //private final PasswordEncoder passwordEncoder;



    /**
     * 로그인 처리
     *
     * @param loginId 로그인 아이디
     * @param password 비밀번호
     * @return 로그인 성공 시 MemberDTO 반환, 실패 시 null 반환
     */
    public MemberDTO login(String loginId, String password) {
        // findByLoginIdWithRoles를 사용하여 회원 조회
        Member member = memberRepository.findByLoginIdWithRoles(loginId);

      /*  if (member != null) {
            // 비밀번호 검증
            if (passwordEncoder.matches(password, member.getPassword())) {
                // 로그인 성공: Member → MemberDTO 변환 후 반환
                return member.toDTO(); // 엔티티의 toDTO() 메서드 사용
            }
        }*/

        // 로그인 실패: null 반환
        return null;
    }
}
