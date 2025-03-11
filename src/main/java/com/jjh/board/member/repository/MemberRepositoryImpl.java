package com.jjh.board.member.repository;

import com.jjh.board.member.entity.Member;
import com.jjh.board.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * loginId로 Member와 MemberRole 조회
     *
     * @param loginId 로그인 아이디
     * @return 조회된 Member 객체 (없으면 null)
     */
    public Member findByLoginIdWithRoles(String loginId) {
        QMember member = QMember.member; // Q클래스 사용

        return queryFactory.selectFrom(member)
                .leftJoin(member.memberRoleList).fetchJoin() // fetch join으로 역할까지 가져옴
                .where(member.loginId.eq(loginId))
                .fetchOne(); // 단일 결과 반환
    }
}