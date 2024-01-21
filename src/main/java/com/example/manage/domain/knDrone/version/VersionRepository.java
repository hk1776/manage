package com.example.manage.domain.knDrone.version;

import com.example.manage.domain.knDrone.DroneInfo;
import com.example.manage.domain.knDrone.DroneUpdateParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Repository
@RequiredArgsConstructor
public class VersionRepository {
    private final SpringDataJpaVersionRepository repository;

    public List<Version> findAll(){
        List<Version> all = repository.findAll();
        return all;
    }
    public Version findById(String id){
        return repository.findByItem(id);
    }
    public void update(String id,VersionUpdateParam param) {
        Optional<Version> version = repository.findById(id);
        version.get().setDroneBoard(param.getDroneBoard());
        version.get().setController(param.getController());
        version.get().setBatteryBoard(param.getBatteryBoard());
        version.get().setChargerOut(param.getChargerOut());
        version.get().setChargerBoard(param.getChargerBoard());
        version.get().setKeyInput(param.getKeyInput());
        version.get().setGimbal(param.getGimbal());
        version.get().setTablet(param.getTablet());
        version.get().setRtk(param.getRtk());
        version.get().setFc(param.getFc());
        version.get().setRc(param.getRc());
        version.get().setGcs(param.getGcs());
        version.get().setManual(param.getManual());
    }

}
