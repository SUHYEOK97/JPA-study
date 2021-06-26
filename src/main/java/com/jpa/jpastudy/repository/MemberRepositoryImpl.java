package com.jpa.jpastudy.repository;

import com.jpa.jpastudy.entity.MemberEntity;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor // PersistenceContext 대체(생략) + private final private final
// 명명규칙 맞출 것 (MemberRepository)
// 인터페이스는 상관없지만 구현체는 맞춰주기
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<MemberEntity> findMemberCustom() {
        return em.createQuery("select m from MemberEntity m")
                .getResultList();
    }
}
