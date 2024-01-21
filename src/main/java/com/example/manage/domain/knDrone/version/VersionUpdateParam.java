package com.example.manage.domain.knDrone.version;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class VersionUpdateParam {
    private String item;
    private String droneBoard;
    private int controller;
    private int batteryBoard;
    private int chargerOut;
    private String chargerBoard;
    private String keyInput;
    private int gimbal;
    private String tablet;
    private int rtk;
    private String fc;
    private String rc;
    private String gcs;
    private String manual;

}
