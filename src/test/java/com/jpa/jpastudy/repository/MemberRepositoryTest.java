package com.jpa.jpastudy.repository;

import com.jpa.jpastudy.entity.MemberEntity;
import com.jpa.jpastudy.entity.TeamEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void findByNameTest() {
        MemberEntity m1 = new MemberEntity(10, "TEST!", null);
        memberRepository.save(m1);

        List<MemberEntity> output = memberRepository.findByUserNameAndAgeGreaterThanEqual("TEST!", 10);
        assertThat(output.get(0).getUserName()).isEqualTo("TEST!");
    }


    @Test
    public void testMember() {

        MemberEntity saved = new MemberEntity(10, "Test1", null);
        MemberEntity found = memberRepository.save(saved);

        MemberEntity getMember = memberRepository.findById(found.getId()).get();

        assertThat(saved).isEqualTo(getMember);

    }

    @Test
    public void testMemberQuery() {

        MemberEntity saved = new MemberEntity(10, "Test1", null);
        MemberEntity found = memberRepository.save(saved);

        List<MemberEntity> user = memberRepository.findUser(saved.getUserName());
        if (!user.isEmpty()) {
            assertThat(saved).isEqualTo(user.get(0));
        }

    }

    @Test
    public void testMemberQueryUserNameList() {

        MemberEntity saved = new MemberEntity(10, "Test1", null);
        MemberEntity found = memberRepository.save(saved);

        List<String> userNameList = memberRepository.findUserNameList();
        if (!userNameList.isEmpty()) {
            for(String str : userNameList){
                System.out.println(str);
            }
        }

    }


}