package com.example.manage.domain.role;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ROLE")
public class Role {
    @Id
    private String id;
    @Column(name = "A_S")
    private String as;
    @Column(name = "AS_S")
    private String asS;
    @Column(name = "SCHEDULE_M")
    private String scheduleM;
    @Column(name = "SCHEDULE_S")
    private String scheduleS;
    @Column(name = "PRODUCE_M")
    private String produceM;
    @Column(name = "PRODUCE_S")
    private String produceS;
    @Column(name = "EDU_M")
    private String eduM;
    @Column(name = "EDU_S")
    private String eduS;
    @Column(name = "DOC_M")
    private String docM;
    @Column(name = "DOC_S")
    private String docS;

    public Role() {

    }
    public Role(String as,String asS, String scheduleM, String scheduleS, String produceM, String produceS, String eduM, String eduS, String docM, String docS) {
        this.as = as;
        this.asS = asS;
        this.scheduleM = scheduleM;
        this.scheduleS = scheduleS;
        this.produceM = produceM;
        this.produceS = produceS;
        this.eduM = eduM;
        this.eduS = eduS;
        this.docM = docM;
        this.docS = docS;
    }


}
