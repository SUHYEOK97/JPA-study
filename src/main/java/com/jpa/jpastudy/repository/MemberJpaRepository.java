package com.jpa.jpastudy.repository;

import com.jpa.jpastudy.entity.MemberEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public MemberEntity save(MemberEntity memberEntity){
        em.persist(memberEntity);
        return memberEntity;
    }

    public MemberEntity find(Long id){
        return em.find(MemberEntity.class, id);
    }
}
