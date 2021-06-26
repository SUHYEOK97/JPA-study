package com.jpa.jpastudy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@ToString(of = {"id", "username", "age"})
public class MemberEntity extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long Id;
    private Integer age;
    private String userName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    public MemberEntity(Integer age, String userName) {
        this.age = age;
        this.userName = userName;
    }

    public MemberEntity(Integer age, String userName, TeamEntity team) {
        this.age = age;
        this.userName = userName;
        if(team != null) addTeam(team);
    }

    public void addTeam(TeamEntity teamEntity){
        this.team = teamEntity;
        team.getMemberList().add(this);
    }
}
