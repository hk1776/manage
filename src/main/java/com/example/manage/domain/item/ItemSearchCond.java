package com.example.manage.domain.item;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ItemSearchCond {

    private String company;
    private String customer;
    private String type;
    private String itemName;
    private String detail;
    private String complete;
    private String input;
    private String output;
    private String select;
    private String status;


    public ItemSearchCond(String company, String customer, String type, String itemName, String detail, String complete,String input,String output, String select,String status) {
        this.company = company;
        this.customer = customer;
        this.type = type;
        this.itemName = itemName;
        this.detail = detail;
        this.complete = complete;
        this.input = input;
        this.output = output;
        this.select = select;
        this.status = status;
    }
}
