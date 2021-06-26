package com.jpa.jpastudy.repository;

import com.jpa.jpastudy.entity.MemberEntity;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberEntity> findMemberCustom();
}
