package com.example.manage.domain.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "LOGIN_ID")
    private String loginId; //로그인 ID
    @NotEmpty
    @Column(name = "NAME")
    private String name; //사용자 이름
    @NotEmpty
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "RANK")
    private String rank;
    @Column(name = "POSITION")
    private String position;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "CALL")
    private String call;

    public Member() {
    }

    public Member(String loginId, String name, String password, String rank, String position, String email, String call) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.rank = rank;
        this.position = position;
        this.email = email;
        this.call = call;
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Member(String rank, String position, String call) {
        this.rank = rank;
        this.position = position;
        this.call = call;
    }
}
