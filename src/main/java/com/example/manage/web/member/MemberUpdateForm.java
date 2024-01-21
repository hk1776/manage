package com.example.manage.web.member;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class MemberUpdateForm {
    private Long id;
    private String loginId; //로그인 ID
    private String name; //사용자 이름
    private String password;
    private String rank;
    private String position;
    private String email;
    private String call;

    public MemberUpdateForm(String loginId, String name, String password, String rank, String position, String email, String call) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.rank = rank;
        this.position = position;
        this.email = email;
        this.call = call;
    }

    public MemberUpdateForm(String rank, String position, String call) {
        this.rank = rank;
        this.position = position;
        this.call = call;
    }

    public MemberUpdateForm() {
    }
}
