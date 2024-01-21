package com.example.manage.domain.knDrone;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Repository
@RequiredArgsConstructor
public class DroneInfoRepository {
    private final SpringDataJpaDroneInfoRepository repository;

    public DroneInfo save(DroneInfo drone) {
        DroneInfo find = findById("1");
        drone.setNum(find.getNum()+1);
        repository.save(drone);
        String[] battery = drone.getBattery().split("/");
        String[] charge = drone.getCharger().split("/");

        find.setBinding(String.valueOf(Integer.parseInt(find.getBinding())+1));//식 시리얼 넘버 대체
        find.setNum(find.getNum()+1);
        find.setDrone(String.valueOf(Integer.parseInt(find.getDrone())+1));
        find.setController(String.valueOf(Integer.parseInt(find.getController())+1));
        find.setBattery(String.valueOf(Integer.parseInt(find.getBattery())+battery.length));
        find.setCharger(String.valueOf(Integer.parseInt(find.getCharger())+charge.length));
        find.setGimbal(String.valueOf(Integer.parseInt(find.getGimbal())+1));
        if(!drone.getRtk().equals("x")){
            find.setRtk(String.valueOf(Integer.parseInt(find.getRtk())+1));
        }
        if(!drone.getKeyInput().equals("x")){
            find.setKeyInput(String.valueOf(Integer.parseInt(find.getKeyInput())+1));
        }

        return drone;
    }
    public List<DroneInfo> findAll(){
        List<DroneInfo> all = repository.findAll();
        return all;
    }

    public List<DroneInfo> company(String param){
        List<DroneInfo> result = new ArrayList<>();
        List<DroneInfo> all = repository.findAll();
        for(DroneInfo i : all) {
            if (i.getCompany().contains(param)) {
                log.info("결과 : {}", i.getCompany());
                result.add(i);
            }
        }
        return result;
    }

    public DroneInfo getNum(String com){
        return repository.findByCompanyLike(com);
    }
    public List<DroneInfo> serial(String param){
       return repository.findBySerialLike(param);
    }
    public List<DroneInfo> drone(String param){
        return repository.findByDroneLike(param);
    }

    public DroneInfo findById(String id) {
        return repository.findById(id).get();
    }


    public void update(DroneInfo drone,DroneUpdateParam param){
        drone.setBattery(param.getBattery());
        drone.setCharger(param.getCharger());
        drone.setGimbal(param.getGimbal());

        Optional<DroneInfo> num = repository.findById("1");
        num.get().setBattery(param.getBatteryCnt());
        num.get().setCharger(param.getChargerCnt());
        num.get().setGimbal(param.getGimbalCnt());
    }
}
