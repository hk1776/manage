package com.example.manage.domain.delivery;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "DELIVERY")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String input;
    private String output;
    private String company;
    private String item;
    private String controller;
    private String option;
    @Column(name = "ITEM_SET")
    private String set;
    private String status;
    private String delay;
    private String serial;

    public Delivery() {
    }

    public Delivery(String input, String output, String company, String item, String controller, String option, String set,String status,String delay,String serial) {
        this.input = input;
        this.output = output;
        this.company = company;
        this.item = item;
        this.controller = controller;
        this.option = option;
        this.set = set;
        this.status = status;
        this.delay =delay;
        this.serial =serial;
    }

    public Delivery(Long id, String input, String output, String company, String item, String controller, String option, String set,String status,String delay) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.company = company;
        this.item = item;
        this.controller = controller;
        this.option = option;
        this.set = set;
        this.status = status;
        this.delay =delay;
    }
}
