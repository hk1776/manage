package com.example.manage.domain.item;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String company;
    private String customer;
    private String phone;
    private String type;
    @Column(name = "ITEM_NAME")
    private String itemName;
    private String detail;
    private String solution;
    private Boolean complete;
    private String manager;
    private String memo;
    private String serial;
    public Item() {
    }

    public Item(Long id, String date, String company, String customer, String phone, String type, String itemName, String detail, String solution, Boolean complete, String manager, String memo,String serial) {
        this.id = id;
        this.date = date;
        this.company = company;
        this.customer = customer;
        this.phone = phone;
        this.type = type;
        this.itemName = itemName;
        this.detail = detail;
        this.solution = solution;
        this.complete = complete;
        this.manager = manager;
        this.memo = memo;
        this.serial =serial;
    }

    public Item(String date, String company, String customer, String phone, String type, String itemName, String detail, String solution, Boolean complete, String manager, String memo,String serial) {
        this.date = date;
        this.company = company;
        this.customer = customer;
        this.phone = phone;
        this.type = type;
        this.itemName = itemName;
        this.detail = detail;
        this.solution = solution;
        this.complete = complete;
        this.manager = manager;
        this.memo = memo;
        this.serial = serial;
    }
}
