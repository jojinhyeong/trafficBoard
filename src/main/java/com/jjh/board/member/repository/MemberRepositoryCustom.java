package com.jjh.board.member.repository;

import com.jjh.board.member.entity.Member;

public interface MemberRepositoryCustom {
    Member findByLoginIdWithRoles(String loginId);
}
