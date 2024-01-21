package com.example.manage.web.item.form;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;


@Data
public class ItemSaveForm {

    private String date;
    private String company;
    private String customer;
    private String phone;
    private String type;
    private String itemName;
    private String detail;
    private String solution;
    private String complete;
    private String manager;
    private String memo;
    private String serial;
    public ItemSaveForm(String date, String company, String customer, String phone, String type, String itemName, String detail, String solution, String complete, String manager, String memo) {
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
