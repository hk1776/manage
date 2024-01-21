package com.example.manage.domain.knDrone.version;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "VERSION")
public class Version {
    @Id
    @Column(name = "ITEM_NAME")
    private String item;
    @Column(name = "DRONE_BOARD")
    private String droneBoard;
    private int controller;
    @Column(name = "Battery_BOARD")
    private int batteryBoard;
    @Column(name = "CHARGER_OUT")
    private int chargerOut;
    @Column(name = "CHARGER_BOARD")
    private String chargerBoard;
    @Column(name = "KEY_INPUT")
    private String keyInput;

    private int gimbal;
    private String tablet;
    private int rtk;
    @Column(name = "FIRMWARE_FC")
    private String fc;
    @Column(name = "FIRMWARE_RC")
    private String rc;
    @Column(name = "FIRMWARE_GCS")
    private String gcs;
    @Column(name = "MANUAL")
    private String manual;

    public Version() {

    }
    public Version(String item, String droneBoard, int controller, int batteryBoard, int chargerOut, String chargerBoard, String keyInput, int gimbal,String tablet,int rtk,String fc,String rc,String gcs,String manual) {
        this.item = item;
        this.droneBoard = droneBoard;
        this.controller = controller;
        this.batteryBoard = batteryBoard;
        this.chargerOut = chargerOut;
        this.chargerBoard = chargerBoard;
        this.keyInput = keyInput;
        this.gimbal = gimbal;
        this.tablet = tablet;
        this.rtk = rtk;
        this.fc = fc;
        this.rc = rc;
        this.gcs = gcs;
        this.manual = manual;
    }


}
