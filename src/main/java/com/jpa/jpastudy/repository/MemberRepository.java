package com.jpa.jpastudy.repository;

import com.jpa.jpastudy.dto.MemberDto;
import com.jpa.jpastudy.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    public List<MemberEntity> findByUserNameAndAgeGreaterThan(String userName, Integer age);
    public List<MemberEntity> findByUserNameAndAgeGreaterThanEqual(String userName, Integer age);

    @Query("select m from MemberEntity m where m.userName = :userName")
    public List<MemberEntity> findUser(@Param("userName") String userName);

    @Query("select m.userName from MemberEntity m")
    public List<String> findUserNameList();

    @Query("select new com.jpa.jpastudy.dto.MemberDto(m.Id, m.userName, t.teamName) from MemberEntity m join TeamEntity t")
    public List<MemberDto> findMemberDto(@Param("userName") String userName);


}
