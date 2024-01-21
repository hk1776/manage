package com.example.manage.domain.knDrone;

import lombok.Data;

@Data
public class DroneUpdateParam {
    private String battery;
    private String charger;
    private String batteryCnt;
    private String chargerCnt;
    private String gimbal;
    private String gimbalCnt;
    private String input;
    private String output;

}
