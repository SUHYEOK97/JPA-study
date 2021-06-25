package com.jpa.jpastudy.repository;

import com.jpa.jpastudy.entity.MemberEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testMember(){

        MemberEntity saved = new MemberEntity(10, "Test1", null);
        MemberEntity found = memberRepository.save(saved);

        MemberEntity getMember = memberRepository.findById(found.getId()).get();

        assertThat(saved).isEqualTo(getMember);

    }


}