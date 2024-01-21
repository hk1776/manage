package com.example.manage.domain.as;

import lombok.Data;

@Data
public class AsUpdateParam {
    private String date;
    private String status;
    private String delay;

    public AsUpdateParam(String date, String status) {

        this.date = date;
        this.status = status;
    }

    public AsUpdateParam() {

    }
}
