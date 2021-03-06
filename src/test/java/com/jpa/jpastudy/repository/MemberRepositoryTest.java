package com.jpa.jpastudy.repository;

import com.jpa.jpastudy.dto.MemberDto;
import com.jpa.jpastudy.entity.MemberEntity;
import com.jpa.jpastudy.entity.TeamEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager em;

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

    @Test
    public void paging(){
        memberRepository.save(new MemberEntity(10, "Member1"));
        memberRepository.save(new MemberEntity(10, "Member2"));
        memberRepository.save(new MemberEntity(10, "Member3"));
        memberRepository.save(new MemberEntity(10, "Member4"));
        memberRepository.save(new MemberEntity(10, "Member5"));

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "userName"));

        Page<MemberEntity> page = memberRepository.findByAge(10, pageRequest);

        //????????? ?????????
        List<MemberEntity> list = page.getContent();

        //????????? ????????? ???
        assertThat(list.size()).isEqualTo(3);
        //?????? ????????? ???
        assertThat(page.getTotalElements()).isEqualTo(5);
        //????????? ??????
        assertThat(page.getNumber()).isEqualTo(0);
        //?????? ????????? ??????
        assertThat(page.getTotalPages()).isEqualTo(2);
        //????????? ?????????????
        assertThat(page.isFirst()).isTrue();
        //?????? ???????????? ??????????
        assertThat(page.hasNext()).isTrue();

        Page<MemberDto> toMap = page.map(memberEntity -> new MemberDto(memberEntity.getId(), memberEntity.getUserName(), memberEntity.getTeam().getTeamName()));



    }

    @Test
    public void slicing(){
        memberRepository.save(new MemberEntity(10, "Member1"));
        memberRepository.save(new MemberEntity(10, "Member2"));
        memberRepository.save(new MemberEntity(10, "Member3"));
        memberRepository.save(new MemberEntity(10, "Member4"));
        memberRepository.save(new MemberEntity(10, "Member5"));

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "userName"));

        Slice<MemberEntity> page = memberRepository.findSliceByAge(10, pageRequest);

        //????????? ?????????
        List<MemberEntity> list = page.getContent();

        // slice??? ?????? ?????? type??? ????????? slice??? ??????????????? ???
        // N + 1 ??? ????????????. < ????????? >
        assertThat(list.size()).isEqualTo(3);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();

    }

    @Test
    public void bulkAgePlus(){
        memberRepository.save(new MemberEntity(12, "Member1"));
        memberRepository.save(new MemberEntity(15, "Member2"));
        memberRepository.save(new MemberEntity(6, "Member3"));
        memberRepository.save(new MemberEntity(18, "Member4"));
        memberRepository.save(new MemberEntity(19, "Member5"));

        int resultCount = memberRepository.bulkAgePlus(10);

        assertThat(resultCount).isEqualTo(4);

    }

    @Test
    public void memberCustomTest(){
        memberRepository.save(new MemberEntity(12, "Member1"));
        memberRepository.save(new MemberEntity(15, "Member2"));
        memberRepository.save(new MemberEntity(6, "Member3"));
        memberRepository.save(new MemberEntity(18, "Member4"));
        memberRepository.save(new MemberEntity(19, "Member5"));

        List<MemberEntity> memberCustom = memberRepository.findMemberCustom();

        for(MemberEntity m : memberCustom){
            System.out.println(m);
        }

    }

    @Test
    public void JpaEventBaseEntity() throws Exception {
        MemberEntity member = new MemberEntity(12, "Member1");
        memberRepository.save(member);

        Thread.sleep(100);
        member.setUserName("test");

        em.flush();
        em.clear();

        MemberEntity findMember = memberRepository.findById(member.getId()).get();

        System.out.println("?????? : " + findMember.getCreatedDate());
        System.out.println("?????? : " + findMember.getLastModifiedDate());
        System.out.println("?????? : " + findMember.getCreatedBy());
        System.out.println("?????? : " + findMember.getLastModifiedBy());

    }


}