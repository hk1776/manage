package com.example.manage.domain.as;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "A_S")
public class As {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String company;
    private String item;
    private String input;
    private String output;
    private String status;
    private String delay;
    private String serial;

    public As() {
    }

    public As(String company, String item, String input, String output, String status,String delay,String serial) {
        this.company = company;
        this.item = item;
        this.input = input;
        this.output = output;
        this.status = status;
        this.delay =delay;
        this.serial = serial;
    }

    public As(Long id, String company, String item, String input, String output, String status,String delay,String serial) {
        this.id = id;
        this.company = company;
        this.item = item;
        this.input = input;
        this.output = output;
        this.status = status;
        this.delay =delay;
        this.serial = serial;
    }
}
