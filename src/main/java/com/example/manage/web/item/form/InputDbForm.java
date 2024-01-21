package com.example.manage.web.item.form;

import lombok.Data;


@Data
public class InputDbForm {

    private String id;
    private String date;
    private String company;
    private String customer;
    private String phone;
    private String type;
    private String itemName;
    private String detail;
    private String solution;
    private Boolean complete;
    private String manager;
    private String memo;

    public InputDbForm(String date, String company, String customer, String phone, String type, String itemName, String detail, String solution, Boolean complete, String manager, String memo) {
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
    }
}
