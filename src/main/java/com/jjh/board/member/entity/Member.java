package com.jjh.board.member.entity;

import com.jjh.board.member.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@NoArgsConstructor //필수
@AllArgsConstructor
@Table(name = "member")
public class Member {

    @Id // 기본 키 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 전략
    private Long id;

    @Column(nullable = false, length = 50, updatable = false,unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(updatable = false)
    private LocalDateTime birthDate;

    @Column(nullable = false)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<MemberRole> memberRoleList = new ArrayList<>();

    public void addRole(MemberRole memberRole) {
        memberRoleList.add(memberRole);
    }

    public MemberDTO toDTO() {
        return MemberDTO.builder()
                .id(this.id)
                .loginId(this.loginId)
                //.password(this.password)
                .name(this.name)
                .birthDate(this.birthDate)
                .email(this.email)
                .roles(this.memberRoleList.stream().map(Enum::name).toList()) // Enum을 문자열로 변환
                .build();
    }

}
