package com.jpa.jpastudy.repository;

import com.jpa.jpastudy.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

}
