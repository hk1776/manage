package com.example.manage.domain.delivery;

import lombok.Data;

@Data
public class DeliUpdateParam {
    private String date;
    private String status;
    private String delay;
    public DeliUpdateParam(String date, String status) {

        this.date = date;
        this.status = status;
    }

    public DeliUpdateParam() {

    }
}
