package com.jpa.jpastudy.repository;

import com.jpa.jpastudy.dto.MemberDto;
import com.jpa.jpastudy.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>, MemberRepositoryCustom {
    List<MemberEntity> findByUserNameAndAgeGreaterThan(String userName, Integer age);
    List<MemberEntity> findByUserNameAndAgeGreaterThanEqual(String userName, Integer age);

    @Query("select m from MemberEntity m where m.userName = :userName")
    List<MemberEntity> findUser(@Param("userName") String userName);

    @Query("select m.userName from MemberEntity m")
    List<String> findUserNameList();

    @Query("select new com.jpa.jpastudy.dto.MemberDto(m.Id, m.userName, t.teamName) from MemberEntity m join TeamEntity t")
    List<MemberDto> findMemberDto(@Param("userName") String userName);

    @Query(value = "select m from MemberEntity m left join m.team t", countQuery = "select count(m) from MemberEntity m")
    Page<MemberEntity> findByAge(Integer age, Pageable pageable);

    Slice<MemberEntity> findSliceByAge(Integer age, Pageable pageable);

    @Modifying(clearAutomatically = true) // 자동으로 영속성 컨텍스트에 있는 1차캐시를 flush, clear해준다.
    @Query("update MemberEntity m set m.age = m.age+1 where m.age >= :age")
    int bulkAgePlus(@Param("age")int age);

}
