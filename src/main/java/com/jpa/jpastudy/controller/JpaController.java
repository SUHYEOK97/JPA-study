package com.jpa.jpastudy.controller;

import com.jpa.jpastudy.dto.MemberDto;
import com.jpa.jpastudy.entity.MemberEntity;
import com.jpa.jpastudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class JpaController {

    private final MemberRepository memberRepository;

    // Entity를 외부에 노출하지 말 것
    @GetMapping("/members")
    public Page<MemberDto> list(Pageable pageable){
        return memberRepository.findAll(pageable)
                .map(memberEntity -> new MemberDto(memberEntity.getId(), memberEntity.getUserName(), null));
    }

    @GetMapping("/listDefaultPage")
    public Page<MemberEntity> listDefaultPage(@PageableDefault(size = 5) Pageable pageable){
        return memberRepository.findAll(pageable);
    }

    @PostConstruct
    public void init(){
        for(int i=1; i<100; i++){
            MemberEntity member = new MemberEntity(i, "name"+i);
            memberRepository.save(member);
        }
    }

}
