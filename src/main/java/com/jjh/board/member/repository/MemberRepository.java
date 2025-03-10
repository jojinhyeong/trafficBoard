package com.jjh.board.member.repository;

import com.jjh.board.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    //@EntityGraph(attributePaths = {"memberRoleList"})
    //@Query("select m from Member m where m.loginid = :loginid")
    //Member getWithRoles(@Param("loginid") String loginid);

    Optional<Member> findByLoginId(String loginId);
}
