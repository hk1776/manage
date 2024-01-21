package com.example.manage.domain.knDrone;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "DRONE_INFO")
public class DroneInfo {
    @Id
    private String serial;
    @GeneratedValue
    private int num;
    private String drone;
    private String binding;
    private String controller;
    private String battery;
    private String charger;
    private String tablet;
    private String rtk;
    @Column(name = "KEY_INPUT")
    private String keyInput;
    private String board;
    private String firmware;
    private String manual;
    private String note;
    private String company;
    private String gimbal;

    public DroneInfo() {
    }

    public DroneInfo(String serial, int num, String drone, String binding, String controller, String battery, String charger, String tablet, String rtk, String keyInput, String board, String firmware, String manual, String note,String company,String gimbal) {
        this.serial = serial;
        this.num = num;
        this.drone = drone;
        this.binding = binding;
        this.controller = controller;
        this.battery = battery;
        this.charger = charger;
        this.tablet = tablet;
        this.rtk = rtk;
        this.keyInput = keyInput;
        this.board = board;
        this.firmware = firmware;
        this.manual = manual;
        this.note = note;
        this.company = company;
        this.gimbal = gimbal;
    }

}
