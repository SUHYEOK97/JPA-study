package com.jpa.jpastudy.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@SpringBootTest
@Rollback(false)
class MemberEntityTest {
    @PersistenceContext
    EntityManager em;

    @Test
    public void addTeamTest(){
        TeamEntity teamA = new TeamEntity("TeamA");
        TeamEntity teamB = new TeamEntity("TeamB");

        em.persist(teamA);
        em.persist(teamB);

        MemberEntity memberEntity1 = new MemberEntity(10, "TEST1", teamA);
        MemberEntity memberEntity2 = new MemberEntity(12, "TEST2", teamB);
        MemberEntity memberEntity3 = new MemberEntity(16, "TEST3", teamA);
        MemberEntity memberEntity4 = new MemberEntity(20, "TEST4", teamB);

        em.persist(memberEntity1);
        em.persist(memberEntity2);
        em.persist(memberEntity3);
        em.persist(memberEntity4);

        em.flush();
        em.clear();

        List<MemberEntity> list = em.createQuery("select m from MemberEntity m", MemberEntity.class)
                .getResultList();

        for(MemberEntity m : list){
            System.out.println("member = " + m.getUserName());
        }



    }

}