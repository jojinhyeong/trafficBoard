package com.jjh.board.member.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MemberDTO {

    private Long id;
    private String loginId;
    private String name;
    private LocalDateTime birthDate;
    private String email;
    private String password; // 비밀번호 필드 추가
    private List<String> roles;

    @Builder
    public MemberDTO(Long id, String loginId, String name, LocalDateTime birthDate, String email, String password, List<String> roles) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password; // 추가된 필드
        this.roles = roles;
    }
}

